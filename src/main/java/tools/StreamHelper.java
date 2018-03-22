package tools;

import model.Data.RawData;

import java.util.List;
import java.util.stream.Collectors;

public class StreamHelper {

    public static List<RawData> getRawDataByFileName(String fileName,List<RawData> data){
        List<RawData> outList = data.stream().filter(rawData1 -> rawData1.getFileName().equals(fileName)).collect(Collectors.toList());
        return outList;
    }
}
