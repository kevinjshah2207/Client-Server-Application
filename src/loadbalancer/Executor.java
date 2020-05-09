package loadbalancer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Executor implements Runnable {
    Socket socketServer;
    Socket clientSocket;

    public Executor(Socket serverSocket,Socket clientSocket) {
        this.socketServer = serverSocket;
        this.clientSocket = clientSocket;
    }

    public void run(){
        String theLine = null;
        try {

            BufferedReader inClient = null;
            PrintStream outClient = null;
            inClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outClient = new PrintStream(clientSocket.getOutputStream());

            BufferedReader inServer = null;
            PrintStream outServer = null;
            
            outServer = new PrintStream(socketServer.getOutputStream());
            inServer = new BufferedReader(new InputStreamReader(socketServer.getInputStream()));
            
            theLine = inClient.readLine();
            outServer.println(theLine);
            theLine = inServer.readLine();
            outClient.println(theLine);
            
            clientSocket.close();
            socketServer.close();

        } catch (IOException ex) {
            System.out.println(ex+"");
        }

    }
}
