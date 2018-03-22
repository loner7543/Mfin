package ru.ssau.controllers;

import model.Calculation.Converter;
import model.Data.Data;
import model.Data.RawData;
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

import java.util.LinkedList;
import java.util.List;

@Controller
public class ProfileController {

    private final FilenameService filenameService;
    private double[] heights;

    @Autowired
    public ProfileController(FilenameService filenameService) {
        this.filenameService = filenameService;
    }

    /*
* Рассчитывает высоты в зависимости от выбранного режима настройки
* */
    @RequestMapping(value = "/calculateCluglogramme", method = RequestMethod.POST)
    public ResponseEntity calculateCruglogramme(@RequestParam(value = RawData.FILENAME) String fileName,
                                                @RequestParam(value = RawData.MODE) String mode){
       int minBorder;
       int maxBorder;
        try {
            boolean type;
            RawData elem = StreamHelper.getRawDataByFileName(fileName,filenameService.getRawData()).get(0);
            if (mode.equals("grann")){// гранность
                type = false;
                minBorder=16;
                maxBorder=150;
            }
            else {
                type=true;// волнистость
                minBorder=0;
                maxBorder=15;
            }
            Converter converter =new Converter(elem,type);
           Data data = converter.createData(type,minBorder,maxBorder,1);//1- номер сечения. Спросить откуда брать сечение
            heights=data.getH();
            return new  ResponseEntity(heights, HttpStatus.OK);
        }
        catch (Exception e){
            return new  ResponseEntity(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /*
выгружает высоты с грфика амплитуд на экране "Статистика"
 */
    @RequestMapping(value = "/uploadHeights", method = RequestMethod.GET)
    public ResponseEntity writeProfile() {
        ResponseEntity responseEntity;
        try{
            if (heights!=null){
                int i = 1;
                List<String> outH = new LinkedList<>();
                for (double h:heights){
                    outH.add(String.valueOf(i).concat("  ").concat(String.valueOf(h)));
                    i++;
                }
                FileUtils.writeFile("heights.txt",outH);
                responseEntity = new ResponseEntity(HttpStatus.OK);
                return responseEntity;
            }
            else {
                responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                return responseEntity;
            }
        }
        catch (Exception e){
            responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            return responseEntity;
        }

    }

    /*
* Отдает точки при изменении масштаба профиля
* */
    @RequestMapping(value = "/getCirclePoints", method = RequestMethod.GET)
    public ResponseEntity getCirclePoints(@RequestParam(value = "radius") int radius ){
//        List<Point> points = drawServiceImpl.drawCircumferencePoints(radius,1,1);
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
    * Записывает либо круглограмму гранности либо волнистости в файл
    * */
    @RequestMapping(value = "/uploadRad", method = RequestMethod.GET)
    public ResponseEntity writeCruglogramme(){
        return new ResponseEntity(HttpStatus.OK);
    }
}
