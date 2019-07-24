package org.brijframework.network.app.testing.ftp;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPCreateDirDemmo {

	private static void showServerReply(FTPClient ftpClient) {
		String[] replies = ftpClient.getReplyStrings();
		if (replies != null && replies.length > 0) {
			for (String aReply : replies) {
				System.out.println("SERVER: " + aReply);
			}
		}
	}

	public static void main(String[] args) {
		String server = "192.168.0.6";
		int port = 2101;
		String user = "ram-ftp";
		String pass = "12345";

		FTPClient ftpClient = new FTPClient();

		try {

			ftpClient.connect(server, port);
			showServerReply(ftpClient);

			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				System.out.println("Operation failed. Server reply code: " + replyCode);
				return;
			}

			boolean success = ftpClient.login(user, pass);
			showServerReply(ftpClient);

			if (!success) {
				System.out.println("Could not login to the server");
				return;
			}

			// Creates a directory
			String dirToCreate = "/upload123";
			success = ftpClient.makeDirectory(dirToCreate);
			showServerReply(ftpClient);

			if (success) {
				System.out.println("Successfully created directory: " + dirToCreate);
			} else {
				System.out.println("Failed to create directory. See server's reply.");
			}

			// logs out
			ftpClient.logout();
			ftpClient.disconnect();

		} catch (IOException ex) {
			System.out.println("Oops! Something wrong happened");
			ex.printStackTrace();
		}
	}

}
