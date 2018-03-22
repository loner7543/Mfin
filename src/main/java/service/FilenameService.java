package service;

import model.Data.RawData;
import model.Exception.FileFormatException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface FilenameService {

    List<String> readDATFiles(String path) throws IOException, FileFormatException;

    List<String> getFileNames();

    void setFileNames(List<String> fileNames);

    List<RawData> getRawData();

    void setRawData(List<RawData> rawData);
}
