
package model.Data;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;


public class RawData implements Serializable
{
    @XmlTransient
    public static final String FILENAME="fileName";

    @XmlTransient
    public static final String MODE="mode";


    private String fileName;
    private double surfaceRadius;               //радиус фотоприемника LFP
    private double emitterYOffset;              //смещение осветителя Y0
    private double initialEmitterHeightPosition;//Zmin осветителя
    private double emitterHeightStep;           //шаг осветителя
    private double minReflectedLightCoord;
    private double maxReflectedLightCoord;
    private int discretePoints;                 //кол-во точек на сечение NFI
    private int radialProfilesNumber;           //кол-во радиальных сечений
    private double[] xOffset;                   //пси
    private double[] yOffset;                   //тета

    public RawData( double surfaceRadius, double emitterXOffset,
                    double initialEmitterHeightPosition, double emitterHeightStep,
                    double minReflectedLightCoord, double maxReflectedLightCoord,
                    int discretePoints, int radialProfilesNumber,
                    double[] xOffset, double[] yOffset,String name)
   {
        this.surfaceRadius=surfaceRadius;
        this.emitterYOffset=emitterXOffset;
        this.initialEmitterHeightPosition=initialEmitterHeightPosition;
        this.emitterHeightStep=emitterHeightStep;
        this.minReflectedLightCoord=minReflectedLightCoord;
        this.maxReflectedLightCoord=maxReflectedLightCoord;
        this.discretePoints=discretePoints;
        this.radialProfilesNumber=radialProfilesNumber;
        this.xOffset=xOffset;
        this.yOffset=yOffset;
        this.fileName = name;
    }

    public double getSurfaceRadius()
    {
        return surfaceRadius;
    }
    public double getEmitterYOffset()
    {
        return emitterYOffset;
    }
    public double getInitialEmitterHeightPosition()
    {
        return initialEmitterHeightPosition;
    }
    public double getEmitterHeightStep()
    {
        return emitterHeightStep;
    }
    public double getMinReflectedLightCoord()
    {
        return minReflectedLightCoord;
    }
    public double getMaxReflectedLightCoord()
    {
        return maxReflectedLightCoord;
    }
    public int getDiscretePoints()
    {
        return discretePoints;
    }
    public int getRadialProfilesNumber()
    {
        return radialProfilesNumber;
    }
    public double[] getXOffset()
    {
        return xOffset;
    }
    public double[] getYOffset()
    {
        return yOffset;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

