package com.manage.app;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     *
     * @param args The arguments of the program.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        Thread thread1= new Thread(new Runnable(){

            @Override
            public void run() {
            Server.main(new String[]{"6001"});

            }
        });

        Thread thread2= new Thread(new Runnable(){

            @Override
            public void run() {
                Server.main(new String[]{"6002"});

            }
        });

        Thread thread3= new Thread(new Runnable(){

            @Override
            public void run() {
                Server.main(new String[]{"6003"});

            }
        });

        Thread thread4= new Thread(new Runnable(){

            @Override
            public void run() {
                Server.main(new String[]{"6004"});

            }
        });

        Thread loadthread= new Thread(new Runnable(){

            @Override
            public void run() {
                Loadbalancer.main(new String[0]);

            }
        });

        thread1.start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        thread2.start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        thread3.start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        thread4.start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        loadthread.start();




    }
}
