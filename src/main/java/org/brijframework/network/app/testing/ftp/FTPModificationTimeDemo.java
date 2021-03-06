package org.brijframework.network.app.testing.ftp;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.net.ftp.FTPClient;

/**
 * This program demonstrates how to get and set modification time
 * of a specific file on a FTP server using Apache Commons Net API.
 * @author www.codejava.net
 *
 */
public class FTPModificationTimeDemo {

	public static void main(String[] args) {
		String server = "192.168.0.6";
		int port = 2101;
		String user = "ram-ftp";
		String pass = "12345";

		FTPClient ftpClient = new FTPClient();

		try {
			ftpClient.connect(server, port);

			ftpClient.login(user, pass);

			ftpClient.enterLocalPassiveMode();

			// get modification time
			String filePath = "Upload/Picture.png";

			String time = ftpClient.getModificationTime(filePath);

			System.out.println("Server Reply: " + time);

			printTime(time);

			// set modification time
			time = "20130816162432";
			ftpClient.setModificationTime(filePath, time);
			System.out.println(ftpClient.getReplyString());

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

	static void printTime(String time) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			String timePart = time.split(" ")[1];
			Date modificationTime = dateFormat.parse(timePart);
			System.out.println("File modification time: " + modificationTime);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
	}
}