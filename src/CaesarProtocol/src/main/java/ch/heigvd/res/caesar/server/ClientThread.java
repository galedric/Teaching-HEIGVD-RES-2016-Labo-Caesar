package ch.heigvd.res.caesar.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientThread extends Thread {

    private Socket socket;
    private InputStream input;
    private OutputStream output;
    private CaesarServer server;

    public ClientThread(Socket socket, CaesarServer server) {
        try {
            this.socket = socket;
            this.server = server;
            input = socket.getInputStream();
            output = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }

    public void send(/*Frame frame*/) {
        /* output.write(frame.serialize()); */
    }

    public void broadcast(/*Frame frame*/) {

    }
}
