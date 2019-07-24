package org.brijframework.network.socket.ftp;

/**
 *  Copyright (C) 2000 Enterprise Distributed Technologies Ltd.
 *
 *
 *  Change Log:
 *
 *        $Log: FTPTransferType.java,v $
 *        Revision 1.3  2001/10/09 20:54:08  bruceb
 *        No change
 *
 *        Revision 1.1  2001/10/05 14:42:04  bruceb
 *        moved from old project
 *
 *
 */

/**
 * Enumerates the transfer types possible. We support only the two common types,
 * ASCII and Image (often called binary).
 *
 * @author Bruce Blackshaw
 * @version $Revision: 1.3 $
 *
 */
public class FTPTransferType {

	/**
	 * Represents ASCII transfer type
	 */
	public static FTPTransferType ASCII = new FTPTransferType();

	/**
	 * Represents Image (or binary) transfer type
	 */
	public static FTPTransferType BINARY = new FTPTransferType();

	/**
	 * The char sent to the server to set ASCII
	 */
	static String ASCII_CHAR = "A";

	/**
	 * The char sent to the server to set BINARY
	 */
	static String BINARY_CHAR = "I";

	/**
	 * Private so no-one else can instantiate this class
	 */
	private FTPTransferType() {
	}
}
