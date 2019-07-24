package org.brijframework.network.app.testing.http;

import java.io.IOException;

import org.brijframework.network.util.HttpUtil;

public class HttpDownloader {

	public static void main(String[] args) {
		String fileURL = "http://jdbc.postgresql.org/download/postgresql-9.2-1002.jdbc4.jar";
		String saveDir = "F:/";
		try {
			HttpUtil.downloadFile(fileURL, saveDir);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}