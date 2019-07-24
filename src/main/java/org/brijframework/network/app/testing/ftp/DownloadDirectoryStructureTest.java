package org.brijframework.network.app.testing.ftp;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.brijframework.network.util.FTPUtil;

public class DownloadDirectoryStructureTest {

	public static void main(String[] args) {
		String server = "192.168.0.6";
		int port = 2101;
		String user = "ram-ftp";
		String pass = "12345";

		FTPClient ftpClient = new FTPClient();

		try {
			// connect and login to the server
			ftpClient.connect(server, port);
			ftpClient.login(user, pass);

			// use local passive mode to pass firewall
			ftpClient.enterLocalPassiveMode();

			System.out.println("Connected");

			String remoteDirPath = "/";
			String saveDirPath = "E:\\Download";

			FTPUtil.downloadDirStructure(ftpClient, remoteDirPath, "",
					saveDirPath);

			// log out and disconnect from the server
			ftpClient.logout();
			ftpClient.disconnect();

			System.out.println("Disconnected");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}