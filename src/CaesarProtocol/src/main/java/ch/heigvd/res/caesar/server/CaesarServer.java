package ch.heigvd.res.caesar.server;

import ch.heigvd.res.caesar.client.*;
import ch.heigvd.res.caesar.protocol.Protocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * @author Olivier Liechti (olivier.liechti@heig-vd.ch)
 */
public class CaesarServer {

    private static final Logger LOG = Logger.getLogger(CaesarServer.class.getName());
    private static LinkedList<Socket> sockets;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tH:%1$tM:%1$tS::%1$tL] Server > %5$s%n");
        //LOG.info("Caesar server starting...");
        //LOG.info("Protocol constant: " + Protocol.CLIENT_HELLO);

        Socket socket;
        sockets = new LinkedList();

        try {
            ServerSocket serverSocket = new ServerSocket(2303);

            while (true) {
                socket = serverSocket.accept();

                sockets.add(socket);

                new ClientThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(/*Frame frame*/) {
        for (Socket s : sockets) {
            try {
                s.getOutputStream().write(/*frame.serialize()*/);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
