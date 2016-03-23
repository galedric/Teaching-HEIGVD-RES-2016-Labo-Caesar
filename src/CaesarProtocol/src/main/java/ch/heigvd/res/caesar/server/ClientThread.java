package ch.heigvd.res.caesar.server;

import ch.heigvd.res.caesar.protocol.CipherInputStream;
import ch.heigvd.res.caesar.protocol.CipherOutputStream;
import ch.heigvd.res.caesar.protocol.Protocol;
import ch.heigvd.res.caesar.protocol.frame.*;
import sun.plugin.dom.exception.InvalidStateException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Random;

public class ClientThread extends Thread {
    enum State {WAITING_HELLO, WAITING_KEY, WAITING_LOGIN, READY}

    private Socket socket;
    private CipherInputStream cipherInput;
    private DataInputStream input;
    private CipherOutputStream cipherOutput;
    private DataOutputStream output;
    private State state;
    private String username;
    private int serverKey;

    public ClientThread(Socket socket) {
        try {
            state = State.WAITING_HELLO;
            this.socket = socket;
            cipherInput = new CipherInputStream(socket.getInputStream());
            input = new DataInputStream(cipherInput);
            cipherOutput = new CipherOutputStream(socket.getOutputStream());
            output = new DataOutputStream(cipherOutput);

            Random r = new Random();
            serverKey = Math.abs(r.nextInt()) % Protocol.bound + 2;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                int frameLength = input.readInt();
                byte[] frame = new byte[frameLength];

                input.readFully(frame);

                switch (frame[0]) {
                    case Protocol.CLIENT_HELLO:
                        if (state != State.WAITING_HELLO) {
                            protocolError();
                        }

                        ClientHelloFrame client = ClientHelloFrame.unserialize(frame);

                        if(client.version != Protocol.VERSION){
                            protocolError();
                        }

                        BigInteger publicKey = BigInteger.valueOf(Protocol.g).modPow(BigInteger.valueOf(serverKey), BigInteger.valueOf(Protocol.p));
                        ServerHelloFrame server = new ServerHelloFrame(client.version, Protocol.g, Protocol.p, publicKey.intValueExact());
                        send(server);

                        // Si tout ok
                        state = State.WAITING_KEY;

                        break;

                    case Protocol.KEY:
                        if (state != State.WAITING_KEY) {
                            protocolError();
                        }

                        KeyFrame key = KeyFrame.unserialize(frame);

                        // Chiffrement
                        int k = BigInteger.valueOf(key.clientKey).modPow(BigInteger.valueOf(serverKey), BigInteger.valueOf(Protocol.p)).intValueExact();

                        cipherInput.setKey(k);
                        cipherOutput.setKey(k);

                        // Si tout ok
                        state = State.WAITING_LOGIN;

                        break;

                    case Protocol.LOGIN:
                        if (state != State.WAITING_LOGIN) {
                            protocolError();
                        }

                        LoginFrame log = LoginFrame.unserialize(frame);

                        // Vérifier username
                        if(!CaesarServer.checkUsername(log.username)){
                            send(new LoginFailFrame("Username already taken"));
                            continue;
                        }

                        send(new LoginOkFrame(CaesarServer.numClients()));
                        username = log.username;

                        CaesarServer.broadcast(new MessageFrame("-SERV-", username + " has joined."));

                        // Si tout ok
                        state = State.READY;
                        break;

                    case Protocol.SEND:
                        if (state != State.READY) {
                            protocolError();
                        }

                        SendFrame s = SendFrame.unserialize(frame);
                        CaesarServer.broadcast(new MessageFrame(username, s.message));

                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();

                CaesarServer.removeClient(this);

                if(username != null){
                    try {
                        CaesarServer.broadcast(new MessageFrame("-SERV-", username + " has left."));
                    } catch (IOException o){}
                }

                try {
                    socket.close();
                } catch (IOException i) {}

                return;
            }
        }
    }

    public void send(Frame frame) throws IOException {
        System.out.println("Sending: " + frame);
        byte[] buffer = frame.serialize();
        output.writeInt(buffer.length);
        output.write(buffer);
    }

    public void protocolError() throws IOException {
        socket.close();

        // Etc...
        throw new InvalidStateException("Error");
    }

    public String getUsername() {
        return username;
    }

    public OutputStream getOutputStream() {
        return output;
    }

    public boolean isReady(){
        return (state == State.READY);
    }
}
