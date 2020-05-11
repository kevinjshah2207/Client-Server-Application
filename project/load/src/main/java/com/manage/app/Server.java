package com.manage.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;


public class Server {

    ServerSocket listenSocket = null;

    private String uri;
    private MongoClientURI clientURI;
    private MongoClient mongoClient;

    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> collection,collection1,collection2,collection3,collection4;


    public Server(int port) {
        try {
            listenSocket = new ServerSocket(port);

            uri = "mongodb+srv://admin:admin@databaseserver-0m5c4.mongodb.net";
            clientURI = new MongoClientURI(uri);
            mongoClient = new MongoClient(clientURI);

            mongoDatabase = mongoClient.getDatabase("serverdb");
            String tep="serverdb"+String.valueOf(port);
            collection = mongoDatabase.getCollection(tep);
            collection1 = mongoDatabase.getCollection("serverdb6001");
            collection2 = mongoDatabase.getCollection("serverdb6002");
            collection3 = mongoDatabase.getCollection("serverdb6003");
            collection4 = mongoDatabase.getCollection("serverdb6004");


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
            String argument = null;
            String[] arguments = null;
            Document document = null;

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

                MongoCursor<Document> cursor = collection.find().iterator();
                int len=(int)collection.countDocuments();
                int ind=0;
                theLine="";
                try {
                    while (cursor.hasNext()) {

                        Document temp = cursor.next();
                        String tempo= temp.get("name").toString();
                        tempo=tempo+";"+ temp.get("no").toString();
                        tempo=tempo+";"+ temp.get("age").toString();
                        if(ind<len&&ind>0){
                            theLine=theLine+":";
                        }
                        theLine=theLine+tempo;
                        ind++;
                    }
                } finally {
                    cursor.close();
                }
                
                    argument=in.readLine();
                    if(argument.equals("read")){
                        System.out.println("Read Request at Server from " + clientSocket.getPort());
                    }
                    else if(argument.equals("exit"))
                    {
                        theLine = argument;
                        
                    }
                    else if(argument!=null)
                    {
                        System.out.println("Write Request at server from " + clientSocket.getPort());
                        arguments = argument.split(";");
                        document = new Document("name",arguments[0]);
                        document.append("no", arguments[1]);
                        document.append("age", arguments[2]);

                        collection1.insertOne(document);
                        collection2.insertOne(document);
                        collection3.insertOne(document);
                        collection4.insertOne(document);

                        theLine = "success";
                    }

                    out.println(theLine);
                

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
