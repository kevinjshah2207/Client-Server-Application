package loadbalancer;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws IOException {
        Socket theSocket = null;
        BufferedReader theInputStream = null;
        PrintStream theOutputStream = null;
        String mathProb = null;

        try {
            theSocket = new Socket("127.0.0.1", 6000);
            theOutputStream = new PrintStream(theSocket.getOutputStream());
            theInputStream = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));

            mathProb = Equation.generate();
            theOutputStream.println(mathProb);
            System.out.println(theInputStream.readLine());

        } catch (UnknownHostException e) {
        } catch (IOException e) {
        } finally {
            try {
                if (theSocket != null) {
                    theSocket.close();
                }
                if (theInputStream != null) {
                    theInputStream.close();
                }

                if (theOutputStream != null) {
                    theOutputStream.close();
                }

            } catch (IOException e) {
            }
        }
        Scanner scan=new Scanner(System.in);
        scan.next();
    }
}
