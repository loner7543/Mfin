package ru.ssau.controllers;

import model.Calculation.ConsiderStatistics;
import model.Calculation.Converter;
import model.Data.Data;
import model.Data.RawData;
import model.Data.StatisticsData;
import model.Exception.LittleStatisticalDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.FilenameService;
import tools.FileUtils;
import tools.StreamHelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/*
 * @ author Manukhin A.V.
 */
@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    private StatisticsData statisticsData;

    private final FilenameService filenameService;

    @Autowired
    public IndexController(FilenameService filenameService) {
        this.filenameService = filenameService;
    }

    /*
    * Обрабатывает запрос расчета статистики
    * */
    @RequestMapping(value = "/calculate", method = RequestMethod.POST)
    public ResponseEntity   calculateStatistics() {
        if (filenameService.getRawData() != null && !filenameService.getRawData().isEmpty()) {
            ConsiderStatistics considerStatistics = new ConsiderStatistics();
            try {
                statisticsData = considerStatistics.createStatisticsData(filenameService.getRawData());
            } catch (LittleStatisticalDataException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(statisticsData, HttpStatus.OK);
    }

//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    public  ResponseEntity save(@RequestParam(value = "file") List<CommonsMultipartFile> files) throws IOException {
//        try{
//            fileNames = new ArrayList<String>();
//            rawData = new ArrayList<RawData>(); //тут данные уже прошедшие через парсер
//            if(null != files && files.size() > 0) {
//                for (MultipartFile multipartFile : files) {
//                    InputStream is = multipartFile.getInputStream();
//                    String fileName = multipartFile.getOriginalFilename();
//                    fileNames.add(fileName);
//                    RawData readDataElem = null;
//                        readDataElem = ReaderRawData.readData(fileName,is);
//                    rawData.add(readDataElem);
//                }
//            }
//            return new ResponseEntity(fileNames,HttpStatus.OK);
//        }
//        catch (Exception e){
//            return new ResponseEntity(e,HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }

    /*
    * Обрабатывает запрос загрузки файлов
    * */
    @RequestMapping(value = "/loadFiles", method = RequestMethod.GET)
    public ResponseEntity  loadFiles(){
        ResponseEntity responseEntity;
        try{
            String s= System.getProperty("user.dir");
            String path = s+"\\datasets";
            List<String> fileNames = filenameService.readDATFiles(path);
            responseEntity = new ResponseEntity(fileNames,HttpStatus.OK);
            return responseEntity;
        }
        catch (Exception e){
            responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            return responseEntity;
        }

    }

    /*
    * Отдае имена файлов для компонента
    * */
    @RequestMapping(value = "/getFileNames", method = RequestMethod.GET)
    public ResponseEntity getFileNames(){
        if (!filenameService.getFileNames().isEmpty()){
           return new ResponseEntity(filenameService.getFileNames(),HttpStatus.OK);
        }else return  new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/calculateProfile", method = RequestMethod.POST)
    public ResponseEntity  calculateProfileFromFile(@RequestParam(value = RawData.FILENAME) String fileName){
        if (filenameService.getFileNames()!=null||filenameService.getRawData().size()>0){
            List<RawData> data = StreamHelper.getRawDataByFileName(fileName,filenameService.getRawData());
            return new ResponseEntity(data.get(0),HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

    }



    /*
    * Записывает амплитуды в файл
    * */
    @RequestMapping(value = "/uploadAmplitudes", method = RequestMethod.GET)
    public ResponseEntity writeAmplitudesToFile() throws IOException {
        try{
            int i = 1;
            List<String> amplitudes = new LinkedList<>();
            for (double amplitude:statisticsData.getAmplitudes()){
                amplitudes.add(String.valueOf(i).concat("  ").concat(String.valueOf(amplitude)));
                i++;
            }
            PrintWriter writer = new PrintWriter("amplitudes.txt", "UTF-8");
            for (String s:amplitudes){
                writer.println(s);
            }
            writer.close();
            //FileUtils.writeFile("amplitudes.txt",amplitudes);
            return new ResponseEntity(writer,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /*
    * Обрабатывет ручной ввод статистики
    * */
    @RequestMapping(value = "/manualInput", method = RequestMethod.POST)
    public ResponseEntity manualInput(@RequestParam(value = StatisticsData.AMPLITUDES) String amplitudes){
        try {
        String [] arr = amplitudes.split(" ");
        double[] data = new double[arr.length];
        for (int i=0;i<arr.length;i++){
            data[i]=Double.valueOf(arr[i]);
        }
        ConsiderStatistics consider=new ConsiderStatistics();
            StatisticsData sDat=consider.createStatisticsData(data);
            return new ResponseEntity(sDat,HttpStatus.OK);
        } catch (LittleStatisticalDataException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
