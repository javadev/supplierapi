package com.cs.roomdbapi.ftpclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class FTPFileWriterImpl implements FTPFileWriter {

    private final FTPProperties FTPProperties;

    protected FTPClient ftpClient;

    public boolean open() {
        close();
        log.debug("Connecting and logging in to FTP server.");
        ftpClient = new FTPClient();
        boolean loggedIn = false;
        try {
            ftpClient.connect(FTPProperties.getServer(), FTPProperties.getPort());
            log.debug("FTP connect reply code: '{}' and message '{}'", ftpClient.getReplyCode(), ftpClient.getReplyString());

            ftpClient.enterLocalPassiveMode();
            log.debug("FTP enterLocalPassiveMode reply code: '{}' and message '{}'", ftpClient.getReplyCode(), ftpClient.getReplyString());

            loggedIn = ftpClient.login(FTPProperties.getUsername(), FTPProperties.getPassword());
            log.debug("FTP login reply code: '{}' and message '{}'", ftpClient.getReplyCode(), ftpClient.getReplyString());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return loggedIn;
    }

    public void close() {
        log.debug("Close connecting to FTP server.");

        if (ftpClient != null) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public boolean loadFile(String remotePath, OutputStream outputStream) {
        try {
            log.debug("Trying to retrieve a file from remote path " + remotePath);
            return ftpClient.retrieveFile(remotePath, outputStream);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public boolean saveFile(InputStream inputStream, String folder, String filename) {
        try {
            log.debug("Trying to store a file to destination path " + folder + "/" + filename);

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            log.debug("FTP setFileType reply code: '{}' and message '{}'", ftpClient.getReplyCode(), ftpClient.getReplyString());

            if (!folder.isEmpty()) {
                ftpClient.makeDirectory(folder);
                log.debug("FTP makeDirectory reply code: '{}' and message '{}'", ftpClient.getReplyCode(), ftpClient.getReplyString());

                ftpClient.changeWorkingDirectory(folder);
                log.debug("FTP changeWorkingDirectory reply code: '{}' and message '{}'", ftpClient.getReplyCode(), ftpClient.getReplyString());
            }

            boolean saveResult = ftpClient.storeFile(filename, inputStream);
            log.debug("FTP storeFile reply code: '{}' and message '{}'", ftpClient.getReplyCode(), ftpClient.getReplyString());

            return saveResult;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public boolean isConnected() {
        boolean connected = false;
        if (ftpClient != null) {
            try {
                connected = ftpClient.sendNoOp();
                log.debug("FTP sendNoOp reply code: '{}' and message '{}'", ftpClient.getReplyCode(), ftpClient.getReplyString());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        log.debug("Checking for connection to FTP server. Is connected: " + connected);
        return connected;
    }
}
