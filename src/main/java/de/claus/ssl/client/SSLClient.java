package de.claus.ssl.client;

import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SSLClient implements Runnable{
	private static final Logger logger = LogManager.getLogger(SSLClient.class);
    
	public void run() {
        try {

            SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault()
                    .createSocket("localhost", 5678);
            PrintWriter printWriter = new PrintWriter(socket
                    .getOutputStream());
            logger.debug("Client -> sending...");
            for (int i = 0; i < 100; i++) {
                String message = "Hallo: " + i;
                logger.debug("Client sent: " + message);
                printWriter.println(message);
                printWriter.flush();
                TimeUnit.SECONDS.sleep(1);
            }
            socket.close();
        } catch (Exception e) {
        	logger.error(e);
        }
    }
    
    public static void main(String[] args) {
    	Executors.newSingleThreadExecutor().execute(new SSLClient());
	}
}
