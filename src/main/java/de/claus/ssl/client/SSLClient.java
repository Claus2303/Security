package de.claus.ssl.client;

import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLClient implements Runnable{

    public void run() {
        try {

            SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault()
                    .createSocket("localhost", 5678);
            PrintWriter printWriter = new PrintWriter(socket
                    .getOutputStream());
            System.out.println("Client -> sending...");
            for (int i = 0; i < 100; i++) {
                String message = "Hallo: " + i;
                System.out.println("Client sent: " + message);
                printWriter.println(message);
                printWriter.flush();
                TimeUnit.SECONDS.sleep(1);
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
    	Executors.newSingleThreadExecutor().execute(new SSLClient());
	}
}
