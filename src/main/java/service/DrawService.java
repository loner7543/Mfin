package service;

import model.Point;

import java.util.List;

public interface DrawService {
    public List<Point> getListAxesPoints(double[] borderedFrrquency, int height, int indentionOnWidth, int width);

    public List<Point> drawCircumferencePoints(int radius,int width,int height);

    public int scaling(int radius);

    public List<Point> drawDeflection( int radius, int width, int height);

}
