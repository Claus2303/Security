package de.claus.ssl.server;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.claus.ssl.client.SSLClient;

public class SSLServer implements Runnable{
	private static final Logger logger = LogManager.getLogger(SSLServer.class);
    
	/**
		-Djavax.net.debug=all 
		-Djavax.net.ssl.keyStore="C:\Wks\Git\Security\src\main\config\mykeystore.p12" 
		-Djavax.net.ssl.keyStorePassword=
	 */
    public void run() {
        try {
            SSLServerSocket serverSocket = (SSLServerSocket) SSLServerSocketFactory
                    .getDefault().createServerSocket(5678);
            logger.debug("Server ready..." + serverSocket);

            logger.debug("Supported Cipher Suites: "
                            + Arrays.toString(((SSLServerSocketFactory) SSLServerSocketFactory
                                            .getDefault())
                                            .getSupportedCipherSuites()));

            SSLSocket socket = (SSLSocket) serverSocket.accept();
            SSLSession sslSession = socket.getSession();
            String cipherSuite = sslSession.getCipherSuite();
            logger.info(cipherSuite);

            Scanner scanner = new Scanner(socket.getInputStream());
            logger.debug("Reading...");
            while (scanner.hasNextLine()) {
            	logger.debug("Server received: " + scanner.nextLine());
            }
            scanner.close();

            socket.close();

            serverSocket.close();
        } catch (IOException e) {
            logger.error(e);
        }
    }
    public static void main(String[] args) {
    	 Executors.newSingleThreadExecutor().execute(new SSLServer());
	}
}
