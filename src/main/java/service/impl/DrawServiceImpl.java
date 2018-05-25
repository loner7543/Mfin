package service.impl;

import model.Point;
import org.springframework.stereotype.Service;
import service.DrawService;

import java.util.ArrayList;
import java.util.List;

/*
 * @ author Manukhin A.V.
 */
@Service
public class DrawServiceImpl implements DrawService {
    private double[] H=null;
    private double[] graphicsH=new double[360];
    private int scale;

    /*
    * Точки для осей графика амплитуд
    * */
    @Override
    public List<Point> getListAxesPoints(double[] borderedFrrquency, int height, int indentionOnWidth, int width){
        List<Point> points = new ArrayList<>();
        for(int j=50; j<=height-35; j+=20)
        {
            points.add(new Point(indentionOnWidth,j));
            for(int i=indentionOnWidth+3; i<width; i++) {
                if((i%3)==0)points.add(new Point(i,j));
            }
        }
        return points;
    }

    @Override
    public List<Point> drawCircumferencePoints(int radius,int width,int height){
        ArrayList listPoints=new ArrayList();
        for(int i=0; i<=360; i++)
        {
            listPoints.add(new Point((int)Math.round(radius*Math.cos(i*2*Math.PI/360))+width/2,
                    (int)Math.round(radius*Math.sin(i*2*Math.PI/360))+height/2) );
        }
        return listPoints;
//        for(int i=0; i<360; i++)
//            g.drawLine(((Point)listPoint.get(i)).x,((Point)listPoint.get(i)).y,
//                    ((Point)listPoint.get(i+1)).x,((Point)listPoint.get(i+1)).y);
    }

    //вычисляет радиус и заполняет graphicsH[] в зависимости от масштаба
    @Override
    public int scaling(int radius)
    {
        double maxH=0;
        for(int i=0; i<H.length; i++)
        {
            graphicsH[i]=H[i]*scale;                 //производим масштабирование
            if(maxH<graphicsH[i])maxH=graphicsH[i];  //выбираем maxH
        }
        return(radius-(int)Math.round(maxH))-2;        //вычисляем радиус
    }

    //рисует отклонения в зависимости зи массива graphicsH[]
    @Override
    public List<Point> drawDeflection( int radius, int width, int height)
    {
        ArrayList listPoint=new ArrayList();
        for(int i=0; i<360; i++)
        {
            listPoint.add(new Point((int)Math.round((radius+(int)Math.round(graphicsH[i]))*Math.cos(i*2*Math.PI/360))+width/2,
                    (int)Math.round((radius+(int)Math.round(graphicsH[i]))*Math.sin(i*2*Math.PI/360))+height/2) );
        }
        listPoint.add(listPoint.get(0));
//        for(int i=0; i<360; i++)
//            g.drawLine(((Point)listPoint.get(i)).x,((Point)listPoint.get(i)).y,
//                    ((Point)listPoint.get(i+1)).x,((Point)listPoint.get(i+1)).y);
        return listPoint;

    }
}
