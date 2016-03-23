package ch.heigvd.res.caesar.client;

import ch.heigvd.res.caesar.protocol.CipherInputStream;
import ch.heigvd.res.caesar.protocol.CipherOutputStream;
import ch.heigvd.res.caesar.protocol.Protocol;
import ch.heigvd.res.caesar.protocol.frame.*;
import sun.plugin.dom.exception.InvalidStateException;

import java.io.*;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Logger;

/**
 * @author Olivier Liechti (olivier.liechti@heig-vd.ch)
 */
public class CaesarClient {

    enum State {WAITING_HELLO, LOGIN, READY}

    private static final Logger LOG = Logger.getLogger(CaesarClient.class.getName());
    private static CipherInputStream cipherInput;
    private static DataInputStream input;
    private static CipherOutputStream cipherOutput;
    private static DataOutputStream output;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tH:%1$tM:%1$tS::%1$tL] Client > %5$s%n");
        LOG.info("Caesar client starting...");
        LOG.info("Protocol constant: " + Protocol.CLIENT_HELLO);

        byte[] keys = null;

        try {
            State state = State.WAITING_HELLO;
            Socket socket = new Socket(InetAddress.getLocalHost(), 2303);
            cipherInput = new CipherInputStream(socket.getInputStream());
            input = new DataInputStream(cipherInput);
            cipherOutput = new CipherOutputStream(socket.getOutputStream());
            output = new DataOutputStream(cipherOutput);

            // Envoi du hello
            ClientHelloFrame client = new ClientHelloFrame(Protocol.VERSION);
            send(client);

            while (true) {
                int frameLength = input.readInt();
                byte[] frame = new byte[frameLength];

                input.readFully(frame);

                switch (frame[0]) {
                    case Protocol.SERVER_HELLO:
                        if(state != State.WAITING_HELLO){
                            protocolError();
                        }

                        ServerHelloFrame serv = ServerHelloFrame.unserialize(frame);

                        Random r = new Random();
                        int clientKey = Math.abs(r.nextInt()) % Protocol.bound + 2;

                        BigInteger publicKey = BigInteger.valueOf(serv.g).modPow(BigInteger.valueOf(clientKey), BigInteger.valueOf(serv.p));
                        int k = BigInteger.valueOf(serv.serverKey).modPow(BigInteger.valueOf(clientKey), BigInteger.valueOf(serv.p)).intValueExact();

                        send(new KeyFrame(publicKey.intValueExact()));

                        cipherInput.setKey(k);
                        cipherOutput.setKey(k);

                        state = State.LOGIN;

                        performLogin();

                        break;

                    case Protocol.LOGIN_FAIL:
                        if(state != State.LOGIN){
                            protocolError();
                        }

                        LoginFailFrame fail = LoginFailFrame.unserialize(frame);
                        System.err.println(fail.message);
                        performLogin();
                        break;

                    case Protocol.LOGIN_OK:
                        if(state != State.LOGIN){
                            protocolError();
                        }

                        LoginOkFrame ok = LoginOkFrame.unserialize(frame);

                        System.out.println("You are connected. There are currently " + ok.users + " users connected.");

                        new Thread() {
                            @Override
                            public void run() {
                                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                                while (true) {
                                    try {
                                        String s = reader.readLine();
                                        send(new SendFrame(s));
                                    } catch (IOException e) {
                                        System.exit(-1);
                                    }
                                }
                            }
                        }.start();

                        state = State.READY;
                        break;


                    case Protocol.MESSAGE:
                        if(state != State.READY){
                            protocolError();
                        }

                        MessageFrame message = MessageFrame.unserialize(frame);
                        System.out.println("[" + message.author + "] " + message.message);

                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void send(Frame frame) throws IOException {
        byte[] buffer = frame.serialize();
        output.writeInt(buffer.length);
        output.write(buffer);
    }

    public static void performLogin() throws IOException {
        System.out.print("Username : ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        send(new LoginFrame(reader.readLine()));
    }

    public static void protocolError() throws IOException {
        throw new InvalidStateException("Error");
    }
}
