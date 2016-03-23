package ch.heigvd.res.caesar.server;

import ch.heigvd.res.caesar.client.*;
import ch.heigvd.res.caesar.protocol.Protocol;
import ch.heigvd.res.caesar.protocol.frame.Frame;

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
    private static LinkedList<ClientThread> threads;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tH:%1$tM:%1$tS::%1$tL] Server > %5$s%n");
        //LOG.info("Caesar server starting...");
        //LOG.info("Protocol constant: " + Protocol.CLIENT_HELLO);

        Socket socket;
        threads = new LinkedList();

        try {
            ServerSocket serverSocket = new ServerSocket(2303);

            while (true) {
                ClientThread n = new ClientThread(serverSocket.accept());
                threads.add(n);
                n.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(Frame frame) throws IOException {
        for (ClientThread c : threads) {
            c.send(frame);
        }
    }

    public static boolean checkUsername(String username){
        for (ClientThread c : threads) {
            if(username == null)
                continue;

            if(username.equals(c.getUsername())){
                return false;
            }
        }

        return true;
    }

    public static int numClients(){
        return threads.size();
    }
}
