package org.brijframework.network.app.testing.ftp;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

/**
 * This program demonstrates how to query status information from a FTP server.
 * @author www.codejava.net
 *
 */
public class FTPStatusDemo {

	public static void main(String[] args) {
		String server = "192.168.0.6";
		int port = 2101;
		String user = "ram-ftp";
		String pass = "12345";

		FTPClient ftpClient = new FTPClient();

		try {
			ftpClient.connect(server, port);

			ftpClient.login(user, pass);

			System.out.println("SYSTEM TYPE:\n " + ftpClient.getSystemType());

			System.out.println("SYSTEM STATUS:\n " + ftpClient.getStatus());

			System.out.println("DIRECTORY STATUS:\n " + ftpClient.getStatus("/HNS"));

			System.out.println("FILE STATUS:\n " + ftpClient.getStatus("/Test/PIC4.JPG"));

			ftpClient.logout();

			ftpClient.disconnect();

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}