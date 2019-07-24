package org.brijframework.network.app.testing.ftp;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

/**
 * This program demonstrates how to get and display reply messages from
 * a FTP server.
 * @author www.codejava.net
 *
 */
public class FTPGetServerReplyDemo {

	public static void main(String[] args) {
		String server = "192.168.0.6";
		int port = 2101;
		String user = "ram-ftp";
		String pass = "12345";

		FTPClient ftpClient = new FTPClient();

		try {
			ftpClient.connect(server, port);
			System.out.println(ftpClient.getReplyString());

			ftpClient.login(user, pass);
			System.out.println(ftpClient.getReplyString());

			ftpClient.logout();
			System.out.println(ftpClient.getReplyString());

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