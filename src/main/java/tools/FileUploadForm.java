package tools;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by User on 011 11.07.17.
 */
public class FileUploadForm {

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    private List<MultipartFile> files;
}
