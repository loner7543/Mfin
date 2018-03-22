
package model.Data;
import java.io.Serializable;
import java.util.ArrayList;

public class StatisticsData implements Serializable
{
    private static double tx=2.17;
    private static double sq3=Math.sqrt(3);

    private double point;
    private double avg;
    private int closest;
    private double sko;//по наиболее близкому к усреднённому
    private double syst;//Систематическая составляющая
    private double psum;//Суммарная погрешность
    private double dov;//Доверительный интервал
    private double[] arrayAmplitudes;//массив амплитуд
    private double[] relativeFrequency;//массив относительных вероятностей
    private ArrayList<Double> bordersFrequency;//границы отрезков
    private double valueKxSquare;//величина кси квадрат
    private double levelTovalue;// уровень значимости, если -1,то гепотиза о нормальном законе распределения отклоняется
    private double minLengthBorderc;
    private double mediumValue;// среднее по выборке

    public StatisticsData(double point,double avg,int closest,double sko,double[] arrayAmplitudes, ArrayList<Double> bordersFrequency,
                          double minLengthBorderc, double[] relativeFrequency, double valueKxSquare, double levelTovalue,double mediumValue)
    {
        this.arrayAmplitudes=arrayAmplitudes;
        this.point=point;
        this.avg=avg;
        this.closest=closest;
        this.sko=sko;
        syst=avg-arrayAmplitudes[closest];
        psum=Math.max(Math.sqrt(sko*sko+syst*syst/3), sko+syst);
        dov=(syst+tx*psum)/(syst/sq3+sko);
        this.relativeFrequency=relativeFrequency;
        this.bordersFrequency=bordersFrequency;
        this.minLengthBorderc=minLengthBorderc;
        this.valueKxSquare=valueKxSquare;
        this.levelTovalue=levelTovalue;
        this.mediumValue = mediumValue;
    }
    public double getAvg()
    {
        return avg;
    }
    public double getPoint()
    {
        return point;
    }
    public int getClosest()
    {
        return closest;
    }
    public double getSko()
    {
        return sko;
    }
    public double getSyst()
    {
        return syst;
    }
    public double getPsum()
    {
        return psum;
    }
    public double getDov()
    {
        return dov;
    }
    public double[] getAmplitudes()
    {
        return arrayAmplitudes;
    }
    public double[] getRelativeFrequency()
    {
        return relativeFrequency;
    }
    public ArrayList<Double> getBordersFrequency()
    {
        return bordersFrequency;
    }
    public double getMinLengthBorderc()
    {
        return minLengthBorderc;
    }
    public double getLevelTovalue()
    {
        return levelTovalue;
    }
    public double getValueKxSquare()
    {
        return valueKxSquare;
    }
    public double getMaxAmplitudes()
    {
        double max=Double.NEGATIVE_INFINITY;
        for(int i=0; i<arrayAmplitudes.length; i++)if(arrayAmplitudes[i]>max)max=arrayAmplitudes[i];
        return max;
    }
    public double getMaxRelativeFrequency()
    {
        double max=Double.NEGATIVE_INFINITY;
        for(int i=0; i<relativeFrequency.length; i++)if(relativeFrequency[i]>max)max=relativeFrequency[i];
        return max;
    }


    public double getMediumValue() {
        return mediumValue;
    }

    public void setMediumValue(double mediumValue) {
        this.mediumValue = mediumValue;
    }
}
