package org.brijframework.network.app.testing.ftp;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * This program demonstrates how to calculate total number of sub directories,
 * files and size of a remote directory on a FTP server, using the Apache
 * Commons Net API.
 *
 * @author www.codejava.net
 *
 */
public class FTPDirectoryCalculation {

	/**
	 * This method calculates total number of sub directories, files and size
	 * of a remote directory.
	 * @param ftpClient An instance of the FTPClient
	 * @param parentDir Path of the remote directory.
	 * @param currentDir The current directory (used for recursion).
	 * @return An array of long numbers in which:
	 * - the 1st number is total directories.
	 * - the 2nd number is total files.
	 * - the 3rd number is total size.
	 * @throws IOException If any I/O error occurs.
	 */
	public static long[] calculateDirectoryInfo(FTPClient ftpClient,
			String parentDir, String currentDir) throws IOException {
		long[] info = new long[3];
		long totalSize = 0;
		int totalDirs = 0;
		int totalFiles = 0;

		String dirToList = parentDir;
		if (!currentDir.equals("")) {
			dirToList += "/" + currentDir;
		}

		try {
			FTPFile[] subFiles = ftpClient.listFiles(dirToList);

			if (subFiles != null && subFiles.length > 0) {
				for (FTPFile aFile : subFiles) {
					String currentFileName = aFile.getName();
					if (currentFileName.equals(".")
							|| currentFileName.equals("..")) {
						// skip parent directory and the directory itself
						continue;
					}

					if (aFile.isDirectory()) {
						totalDirs++;
						long[] subDirInfo =
								calculateDirectoryInfo(ftpClient, dirToList, currentFileName);
						totalDirs += subDirInfo[0];
						totalFiles += subDirInfo[1];
						totalSize += subDirInfo[2];
					} else {
						totalSize += aFile.getSize();
						totalFiles++;
					}
				}
			}

			info[0] = totalDirs;
			info[1] = totalFiles;
			info[2] = totalSize;

			return info;
		} catch (IOException ex) {
			throw ex;
		}
	}

	public static void main(String[] args) {
		String host = "192.168.0.6";
		int port = 2101;
		String username = "ram-ftp";
		String password = "12345";

		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(host, port);
			ftpClient.login(username, password);
			ftpClient.enterLocalPassiveMode();

			String remoteDirPath = "/Upload";

			long[] dirInfo = calculateDirectoryInfo(ftpClient, remoteDirPath, "");

			System.out.println("Total dirs: " + dirInfo[0]);
			System.out.println("Total files: " + dirInfo[1]);
			System.out.println("Total size: " + dirInfo[2]);

			ftpClient.logout();
			ftpClient.disconnect();
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}