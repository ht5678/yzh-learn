package sftp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.stereotype.Service;

@Service("sftpService")
public class SFTPServiceImpl implements SFTPService {

    private static final Logger logger = LoggerFactory.getLogger(SFTPServiceImpl.class);

    @Autowired
    DefaultSftpSessionFactory sftpSessionFactory;

    @Override
    public List<String> listFiles(String path) {
        SftpSession sftpSession = sftpSessionFactory.getSession();
        Objects.requireNonNull(sftpSession, "DefaultSftpSessionFactory.getSession() return null");

        List<String> results = null;
//        try {
//            results = Arrays.stream(sftpSession.listNames(path)).collect(Collectors.toList());
//        } catch (IOException e) {
//            logger.error("DefaultSftpSessionFactory.listNames(\"" + path + "\") throw IOException: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            if (sftpSession != null) {
//                sftpSession.close();
//            }
//        }
        return results;
    }

    @Override
    public List<String> getFileInLines(String sftpAbsPath) {
        SftpSession sftpSession = sftpSessionFactory.getSession();
        Objects.requireNonNull(sftpSession, "DefaultSftpSessionFactory.getSession() return null");

        List<String> lines = null;
//        try {
//            lines = new BufferedReader(new InputStreamReader(sftpSession.readRaw(sftpAbsPath), Charset.forName("UTF-8"))).lines().collect(Collectors.toList());
//        } catch (IOException e) {
//            logger.error("DefaultSftpSessionFactory.readRaw(\"" + sftpAbsPath +"\") throw IOException: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            if (sftpSession != null) {
//                sftpSession.close();
//            }
//        }
        return lines;
    }
    
    
    @Override
    public void putFileInBytes(byte[] bytes, String dst) {
        SftpSession sftpSession = sftpSessionFactory.getSession();
        Objects.requireNonNull(sftpSession, "DefaultSftpSessionFactory.getSession() return null");

        try {
            sftpSession.write(new ByteArrayInputStream(bytes), dst);
        } catch (IOException e) {
            logger.error("DefaultSftpSessionFactory.write(InputStream, " + dst + ") throw IOException" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (sftpSession != null) {
                sftpSession.close();
            }
        }
    }
    

    @Override
    public void putFileInString(String content, String dst) {
        SftpSession sftpSession = sftpSessionFactory.getSession();
        Objects.requireNonNull(sftpSession, "DefaultSftpSessionFactory.getSession() return null");

        try {
            sftpSession.write(new ByteArrayInputStream(content.getBytes(Charset.forName("UTF-8"))), dst);
        } catch (IOException e) {
            logger.error("DefaultSftpSessionFactory.write(InputStream, " + dst + ") throw IOException" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (sftpSession != null) {
                sftpSession.close();
            }
        }
    }

    @Override
    public void deleteFile(String fileAbsPath) {
        SftpSession sftpSession = sftpSessionFactory.getSession();
        Objects.requireNonNull(sftpSession, "DefaultSftpSessionFactory.getSession() return null");

        try {
            sftpSession.remove(fileAbsPath);
        } catch (IOException e) {
            logger.error("DefaultSftpSessionFactory.remove(\"" + fileAbsPath + "\") throw IOException" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (sftpSession != null) {
                sftpSession.close();
            }
        }
    }
}
