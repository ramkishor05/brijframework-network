package org.brijframework.network.socket.ftp;

/**
 *  Copyright (C) 2000 Enterprise Distributed Technologies Ltd.
 *
 *
 *  Change Log:
 *
 *        $Log: FTPConnectMode.java,v $
 *        Revision 1.1  2001/10/09 20:53:46  bruceb
 *        Active mode changes
 *
 *        Revision 1.1  2001/10/05 14:42:04  bruceb
 *        moved from old project
 *
 *
 */

/**
 *  Enumerates the connect modes that are possible,
 *  active & PASV
 *
 *  @author     Bruce Blackshaw
 *  @version    $Revision: 1.1 $
 *
 */
public class FTPConnectMode {

  /**
   *   Represents active connect mode
   */
  public static FTPConnectMode ACTIVE = new FTPConnectMode();

  /**
   *   Represents PASV connect mode
   */
  public static FTPConnectMode PASV = new FTPConnectMode();

  /**
   *  Private so no-one else can instantiate this class
   */
  private FTPConnectMode() {
  }
}
