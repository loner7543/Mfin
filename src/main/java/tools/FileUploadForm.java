package tools;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/*
 * @ author Manukhin A.V.
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
