package org.brijframework.network.app.testing.http;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.brijframework.network.beans.MultipartBean;

/**
 * This program demonstrates a usage of the MultipartUtility class.
 * @author www.codejava.net
 *
 */
public class MultipartFileUploader {

	public static void main(String[] args) {
		String charset = "UTF-8";
		File uploadFile1 = new File("C:/Users/rkishor/Desktop/mvel/src/main/resources/META-INF/app/global/i18/default/mvlhtmltempl/FreeTrialEnding.mvel");
		File uploadFile2 = new File("C:/Users/rkishor/Desktop/mvel/src/main/resources/META-INF/app/global/i18/default/mvlhtmltempl/FreeTrialExpired.mvel");
		String requestURL = "https://mail.google.com/mail/u/0/#sent/15878625cc1e0eb3";

		try {
			MultipartBean multipart = new MultipartBean(requestURL, charset);
			
			multipart.addHeaderField("User-Agent", "CodeJava");
			multipart.addHeaderField("Test-Header", "Header-Value");
			
			multipart.addFormField("description", "Cool Pictures");
			multipart.addFormField("keywords", "Java,upload,Spring");
			
			multipart.addFilePart("fileUpload", uploadFile1);
			multipart.addFilePart("fileUpload", uploadFile2);

			List<String> response = multipart.finish();
			
			System.out.println("SERVER REPLIED:");
			
			for (String line : response) {
				System.out.println(line);
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}