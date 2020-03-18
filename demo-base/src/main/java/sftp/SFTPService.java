package sftp;

import java.util.List;

public interface SFTPService {
    public void putFileInBytes(byte[] bytes, String dst);
    List<String> listFiles(String path);
    List<String> getFileInLines(String sftpAbsPath);
    void putFileInString(String content, String dst);
    void deleteFile(String fileAbsPath);
}
