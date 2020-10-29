package de.claus.ssl.server;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

public class SSLServer implements Runnable{

	/**
		-Djavax.net.debug=all 
		-Djavax.net.ssl.keyStore="C:\Wks\Git\Security\src\main\config\mykeystore.p12" 
		-Djavax.net.ssl.keyStorePassword=
	 */
    public void run() {
        try {
            SSLServerSocket serverSocket = (SSLServerSocket) SSLServerSocketFactory
                    .getDefault().createServerSocket(5678);
            System.out.println("Server ready..." + serverSocket);

            System.out
                    .println("Supported Cipher Suites: "
                            + Arrays.toString(((SSLServerSocketFactory) SSLServerSocketFactory
                                            .getDefault())
                                            .getSupportedCipherSuites()));

            SSLSocket socket = (SSLSocket) serverSocket.accept();
            SSLSession sslSession = socket.getSession();
            String cipherSuite = sslSession.getCipherSuite();
            System.out.println(cipherSuite);

            Scanner scanner = new Scanner(socket.getInputStream());
            System.out.println("Reading...");
            while (scanner.hasNextLine()) {
                System.out.println("Server received: " + scanner.nextLine());
            }
            scanner.close();

            socket.close();

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
    	 Executors.newSingleThreadExecutor().execute(new SSLServer());
	}
}
