package service.impl;

import model.Auxiliary.ReaderRawData;
import model.Data.RawData;
import model.Exception.FileFormatException;
import org.springframework.stereotype.Service;
import service.FilenameService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/*
 * @ author Manukhin A.V.
 */
@Service
public class FilenameServiceImpl implements FilenameService {
    private List<String> fileNames;
    private List<RawData> rawData;

//    public FilenameServiceImpl(List<String> fileNames, List<RawData> rawData) {
//        this.fileNames = fileNames;
//        this.rawData = rawData;
//    }

    @Override
    public List<String> readDATFiles(String path) throws IOException, FileFormatException {
        RawData readDataElem;
        fileNames = new ArrayList<>();
        rawData = new ArrayList<>();
        File folder = new File(path);
        for (final File fileEntry : folder.listFiles()) {
            InputStream inputStream = new FileInputStream(fileEntry);
            fileNames.add(fileEntry.getName());
            readDataElem = ReaderRawData.readData(fileEntry.getName(), inputStream);
            rawData.add(readDataElem);
        }
        return fileNames;
    }

    @Override
    public List<String> getFileNames() {
        return fileNames;
    }

    @Override
    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    @Override
    public List<RawData> getRawData() {
        return rawData;
    }

    @Override
    public void setRawData(List<RawData> rawData) {
        this.rawData = rawData;
    }
}
