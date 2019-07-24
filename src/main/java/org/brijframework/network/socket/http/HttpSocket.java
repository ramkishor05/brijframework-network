package org.brijframework.network.socket.http;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.brijframework.util.runtime.ThreadUtil;


public class HttpSocket implements Runnable, Serializable {
	public static final long serialVersionUID = 1;

	private transient ServerSocket serverSocket = null;
	private String httpHeader, body;
	private boolean isClosed = false;

	public HttpSocket(int port, String serverName) {
		try {
			this.httpHeader = this.getHTTPHeader();
			this.body = this.getBodyString(serverName);
			this.serverSocket = new ServerSocket(port);
			System.out.println("[" + new SimpleDateFormat("h:mm:ss a").format(new Date()) + "] " + serverName + " is listening at port " + port + "...");
		} catch (IOException e) {
			System.out.println("Could not create socket at " + port);
		}
	}

	public void run() {
		while (true) {
			try {
				if (this.serverSocket.isClosed()) {
					break;
				}
				Socket clientSocket = this.serverSocket.accept();
				InputStream input = clientSocket.getInputStream();
				this.parse(input);
				PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
				pw.print(this.httpHeader);
				pw.println(this.body);
				pw.close();
				clientSocket.close();
			} catch (Exception e) {
				if (this.isClosed) {
					break;
				}
				if (e instanceof InterruptedException) {
					break;
				} else {
					try {
						ThreadUtil.sleepForSeconds(2);
					} catch (InterruptedException e1) {
						break;
					}
				}
			}
		}
	}

	public void startMonitoring() {
		Thread httpSocketThread = new Thread(this);
		httpSocketThread.start();
	}

	public void close() {
		this.isClosed = true;
		try {
			this.serverSocket.close();
		} catch (Exception ignored) {
		}
	}

	private void parse(InputStream input) {
		StringBuffer request = new StringBuffer(2048);
		int noOfBytesRead;
		byte[] buffer = new byte[2048];
		try {
			noOfBytesRead = input.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			noOfBytesRead = -1;
		}
		for (int j = 0; j < noOfBytesRead; j++) {
			request.append((char) buffer[j]);
		}
	}

	private String getHTTPHeader() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("HTTP/1.1 200 OK\r\n");
		strBuf.append("Content-Type: text/html\r\n");
		strBuf.append("Content-Length: 150\r\n");
		strBuf.append("\r\n");
		return strBuf.toString();
	}

	private String getBodyString(String serverName) {
		return "<html><head><title>HttpSocket</title></head><body><h2>" + serverName + " is doing good</h2></body></html>";
	}

	public static void printUsage() {
		System.out.println("Usage: \tHttpSocket fromPort toPort");
		System.out.println("\twhere");
		System.out.println("\t\tfromPort: First port where socket needs to be created");
		System.out.println("\t\ttoPort: Last port where socket needs to be created (optional)");
	}

	public static void main(String[] args) {
		if (args == null || args.length < 1) {
			printUsage();
			return;
		}
		int fromPort = 80;
		try {
			fromPort = Integer.parseInt(args[0]);
		} catch (Exception e) {
			System.out.println("Could Not parse fromPort");
			printUsage();
			return;
		}
		int toPort = fromPort;
		if (args.length > 1) {
			try {
				toPort = Integer.parseInt(args[1]);
			} catch (Exception e) {
				System.out.println("Could Not parse toPort");
				printUsage();
				return;
			}
		}
		for (int port = fromPort; port <= toPort; port++) {
			HttpSocket httpSocket = new HttpSocket(port, "Port " + port);
			Thread httpSocketThread = new Thread(httpSocket);
			httpSocketThread.start();
		}
	}
}
