package org.brijframework.network.app.testing.ftp;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

/**
 * This program demonstrates how to search for files and directories on a FTP
 * server using the Apache Commons Net API.
 * @author www.codejava.net
 *
 */
public class FTPSearchDemo {

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

			String dirToSearch = "HNS/Website/Articles";

			FTPFileFilter filter = new FTPFileFilter() {

				@Override
				public boolean accept(FTPFile ftpFile) {

					return (ftpFile.isFile() && ftpFile.getName().contains("Java"));
				}
			};

			FTPFile[] result = ftpClient.listFiles(dirToSearch, filter);

			if (result != null && result.length > 0) {
				System.out.println("SEARCH RESULT:");
				for (FTPFile aFile : result) {
					System.out.println(aFile.getName());
				}
			}

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