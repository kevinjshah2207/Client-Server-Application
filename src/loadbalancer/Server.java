package loadbalancer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {

    private Socket clientSocket;
    ServerSocket listenSocket = null;

    public Server(int port) {
        try {
            listenSocket = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        execution();

    }

    void execution() {
        while (true) {

            Socket clientSocket = null;
            BufferedReader in = null;
            PrintStream out = null;
            String theLine = null;

            while (clientSocket == null) {
                try {
                    clientSocket = listenSocket.accept();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            try {

                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintStream(clientSocket.getOutputStream());
                
                theLine = in.readLine();
                System.out.println("Equation : " + theLine + " from " + clientSocket.getPort());
                out.println(theLine + " = " + Equation.calculate(theLine));
                out.println(clientSocket.getPort());
                
                clientSocket.close();

            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Server at port : "+args[0]);
        new Server(Integer.parseInt(args[0]));
    }

}
