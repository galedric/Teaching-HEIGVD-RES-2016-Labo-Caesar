package ch.heigvd.res.caesar.server;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Romain on 23.03.2016.
 */
public class Server {



    public Server() {

    }

    public void broadcast(/*Frame frame*/) {
        for (Socket s : sockets) {
            try {
                s.getOutputStream().write(/*frame.serialize()*/);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}