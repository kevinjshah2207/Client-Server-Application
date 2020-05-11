package com.manage.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

public class Loadbalancer {

    ServerSocket listenSocket = null;

    public Loadbalancer() {
        ArrayList<Integer> Server_port = new ArrayList<Integer>();
        Server_port.add(6001);
        Server_port.add(6002);
        Server_port.add(6003);
        Server_port.add(6004);

        try {
            listenSocket = new ServerSocket(6000);
            int i=0;
            while (true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println("Request at middle server");
                Socket socketServer;
                while(true)
                {
                    try{
                        socketServer = new Socket("127.0.0.1",Server_port.get(i));
                        System.out.println("Assigned server at port number : " + Server_port.get(i));
                        break;
                    }
                    catch(IOException ex)
                    {
                        System.out.println("failed to connect to server at port number : " + Server_port.get(i));
                        i++;
                        if(i>=Server_port.size())
                        {
                            i=0;
                        }
                    }

                }
                new Thread(new Executor(socketServer,clientSocket)).start();
                i++;
                if(i>=Server_port.size())
                {
                    i=0;
                }
            }
        } catch (Exception e) {

        } finally {
            try {
                listenSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Loadbalancer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        new Loadbalancer();
    }

}
