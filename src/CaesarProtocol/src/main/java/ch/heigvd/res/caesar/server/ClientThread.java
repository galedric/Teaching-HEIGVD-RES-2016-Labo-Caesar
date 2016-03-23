package ch.heigvd.res.caesar.server;

import ch.heigvd.res.caesar.protocol.Protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientThread extends Thread {

    private Socket socket;
    private InputStream input;
    private OutputStream output;

    public ClientThread(Socket socket) {
        try {
            this.socket = socket;
            input = socket.getInputStream();
            output = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true){
            byte[] message = null;
            try {
                input.read(message);
            } catch (IOException e){
                e.printStackTrace();
            }

            switch(message[0]){
                case  Protocol.CLIENT_HELLO:
                    break;

                case Protocol.SERVER_HELLO:
                    break;

                case Protocol.SEND:
                    break;

                case Protocol.MESSAGE:
                    break;

                case Protocol.KEY:
                    break;

                case Protocol.LOGIN:
                    break;

                case Protocol.LOGIN_OK:
                    break;

                case Protocol.LOGIN_FAIL:
                    break;
            }
        }
    }

    public void send(/*Frame frame*/) {
        /* output.write(frame.serialize()); */
    }
}
