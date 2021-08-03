package com.cs.roomdbapi.ftpclient;

import java.io.InputStream;
import java.io.OutputStream;

public interface FTPFileWriter {

    /**
     * Connects to a server and tries to log in the user.
     *
     * @return boolean True if successful, False otherwise.
     */
    boolean open();

    /**
     * Logouts the current user and disconnects from the server.
     */
    void close();

    /**
     * Retrieve a file from the ftp server.
     *
     * @param remotePath   Remote path for the file to retrieve.
     * @param outputStream Stream the file is read into.
     * @return boolean True if successful, False otherwise.
     */

    boolean loadFile(String remotePath, OutputStream outputStream);

    /**
     * Store a file on the ftp server.
     *
     * @param inputStream   Stream the new file is read from.
     * @param folder        Remote folder that need to be used for file upload.
     * @param filename        Remote file name.
     * @return boolean True if successful, False otherwise.
     */

    boolean saveFile(InputStream inputStream, String folder, String filename);

    /**
     * Does a NOOP to see if the connection is valid.
     *
     * @return boolean True if connected, False otherwise.
     */

    boolean isConnected();

    void removeFile(String filename);
}
