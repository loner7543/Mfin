package ru.ssau.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * @ author Manukhin A.V.
 */
@Controller
public class DrawingController {

    /*
     * Отдает точки при изменении масштаба профиля
     * */
    @RequestMapping(value = "/getCirclePoints", method = RequestMethod.GET)
    public ResponseEntity getCirclePoints(@RequestParam(value = "radius") int radius ){
//        List<Point> points = drawServiceImpl.drawCircumferencePoints(radius,1,1);
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
