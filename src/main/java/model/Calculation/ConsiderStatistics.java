
package model.Calculation;

import model.Auxiliary.TabularData;
import model.Data.Data;
import model.Data.RawData;
import model.Data.StatisticsData;
import model.Exception.LittleStatisticalDataException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * @ author Manukhin A.V.
 */
public class ConsiderStatistics //extends Thread
{
    private ArrayList<Data> dataSet;
    private int discretePoints;

    private ArrayList<Double> borders;
    private double minLengthBorderc;

    private static final double EPSILON = 0.000001;

    public ConsiderStatistics() {
        dataSet = null;
        discretePoints = 0;
    }

    public StatisticsData createStatisticsData(List<RawData> rawData) throws LittleStatisticalDataException {
        int numberRadial = 1;
        dataSet = new ArrayList<Data>();
        discretePoints = rawData.get(0).getDiscretePoints();
        for (RawData aRawData : rawData) {
            dataSet.add(new Converter(aRawData, false).createData(false, 16, 150,                           numberRadial));
        }
        return this.createStatisticsData();
    }

    public StatisticsData createStatisticsData(double[] arrayAmplitudes) throws LittleStatisticalDataException {
        double avg = avgProfile(arrayAmplitudes);
        double sko = sko(arrayAmplitudes, avg);
        borders = calculationOfBordersInterval(arrayAmplitudes);
        int[] M = calculationFrequency(arrayAmplitudes, borders);
        double[] relativeFrequency = calculationRelativeFrequency(arrayAmplitudes.length, M);
        double KxSquare = calculationKxSquare(arrayAmplitudes.length, M, calculationTheoreticalRelativeFrequency(avg, sko, M.length));
        double levelTovalue = TabularData.checkingForNormalCurve(KxSquare, M.length);
        double mediumValue = Arrays.stream(arrayAmplitudes).sum() / arrayAmplitudes.length;// среднее значение по выборке амплитуд
        return new StatisticsData(0, avg, 0, sko, arrayAmplitudes, borders, minLengthBorderc, relativeFrequency, KxSquare, levelTovalue, mediumValue,M);
    }

    /*
    * Расчитывает статистику по набору файлов
    * */
    public StatisticsData createStatisticsData() throws LittleStatisticalDataException {
        double[] arrayAmplitudes = new double[dataSet.size()];
        for (int i = 0; i < dataSet.size(); i++) arrayAmplitudes[i] = dataSet.get(i).getAmp();
        double point = point(dataSet, discretePoints);
        double avg = avgProfile(arrayAmplitudes);
        int closest = closest(arrayAmplitudes, avg);
        double sko = sko(arrayAmplitudes, arrayAmplitudes[closest]);
        borders = calculationOfBordersInterval(arrayAmplitudes);
        int[] M = calculationFrequency(arrayAmplitudes, borders);
        double[] relativeFrequency = calculationRelativeFrequency(arrayAmplitudes.length, M);
        double KxSquare = calculationKxSquare(arrayAmplitudes.length, M, calculationTheoreticalRelativeFrequency(avg, sko, M.length));
        double levelTovalue = TabularData.checkingForNormalCurve(KxSquare, M.length);
        double mediumValue = Arrays.stream(arrayAmplitudes).sum() / arrayAmplitudes.length;// среднее значение по выборке амплитуд
        return new StatisticsData(point, avg, closest, sko, arrayAmplitudes, borders, minLengthBorderc, relativeFrequency, KxSquare, levelTovalue, mediumValue,M);
    }

    //Погрешность в точке
    private static double point(ArrayList<Data> dataSet, int discretePoints) {
        double ret = Double.NEGATIVE_INFINITY;
        double reti, tmp, av;
        for (int i = 0; i < discretePoints; i++) {
            reti = 0;
            av = 0;
            for (Data aDataSet : dataSet) av += aDataSet.getH()[i];
            av = av / dataSet.size();
            for (Data aDataSet : dataSet) {
                tmp = aDataSet.getH()[i];
                reti += (tmp - av) * (tmp - av);
            }
            reti = Math.sqrt(reti / dataSet.size());
            if (reti > ret) ret = reti;
        }
        return ret;
    }

    //Усреднённый профиль
    private static double avgProfile(double[] arrayAmplitudes) {
        double ret = 0;
        for (double arrayAmplitude : arrayAmplitudes) ret += arrayAmplitude;
        return ret / arrayAmplitudes.length;
    }

    //Профиль, наиболее близкий к усреднённому
    private static int closest(double[] arrayAmplitudes, double avg) {
        int ret = -1;
        double dif = Double.POSITIVE_INFINITY;
        double tmp;
        for (int i = 0; i < arrayAmplitudes.length; i++) {
            tmp = Math.abs(arrayAmplitudes[i] - avg);
            if (tmp < dif) {
                dif = tmp;
                ret = i;
            }
        }
        return ret;
    }

    //Среднеквадратическое отклонение
    private static double sko(double[] arrayAmplitudes, double avg) {
        double ret = 0;
        double tmp;
        for (double arrayAmplitude : arrayAmplitudes) {
            tmp = arrayAmplitude;
            ret += (tmp - avg) * (tmp - avg);
        }
        return Math.sqrt(ret / (arrayAmplitudes.length - 1));
    }

    //---------------------------------------Проверка гепотизы о нориальном распределении----------------------------------------------------------
    //создание отрезков borders-границы отрезков
    private ArrayList<Double> calculationOfBordersInterval(double[] Amp) {
        double maxAmp = Double.NEGATIVE_INFINITY;
        double minAmp = Double.POSITIVE_INFINITY;
        for (double aAmp : Amp) {
            if (maxAmp < aAmp) maxAmp = aAmp;
            if (minAmp > aAmp) minAmp = aAmp;
        }
        int k = (int) Math.round(5 * Math.log10(Amp.length));
        ArrayList<Double> border = new ArrayList<Double>();
        border.add(minAmp - EPSILON);
        for (int i = 1; i <= k; i++) {
            border.add(minAmp + ((maxAmp - minAmp) * i / k));
        }
        return border;
    }

    //M[i] количетво попаданий случайной величины в заданный отрезок
    private int[] calculationFrequency(double[] Amp, ArrayList<Double> borders) throws LittleStatisticalDataException {
        int[] M;
        boolean flagK = false;
        do {
            M = new int[borders.size() - 1];
            int j = 0;
            for (int i = 0; i < M.length; i++) {
                M[i] = frequency(Amp, borders.get(j + 1)) - frequency(Amp, borders.get(j));
                j++;
            }
            for (int i = 0; i < M.length; i++) {
                if (M[i] < 5) {
                    if (i == 0) borders.remove(i + 1);
                    else if (i == M.length - 1) borders.remove(i);
                    else if (M[i - 1] <= M[i + 1]) borders.remove(i);
                    else borders.remove(i + 1);
                    flagK = true;
                    break;
                } else flagK = false;
            }
            if (M.length == 1) flagK = false;
        } while (flagK);

        if (M.length < 4) throw new LittleStatisticalDataException();

        minLengthBorderc = Double.POSITIVE_INFINITY;
        for (int i = 0; i < M.length; i++) {
            if ((borders.get(i + 1) - borders.get(i)) < minLengthBorderc)
                minLengthBorderc = borders.get(i + 1) - borders.get(i);
        }

        return M;
    }

    //вероятность попадания
    private double[] calculationRelativeFrequency(int n, int[] M) {
        double[] relativeFrequency = new double[M.length];
        for (int i = 0; i < M.length; i++) {
            relativeFrequency[i] = M[i] / (double) n;
        }
        return relativeFrequency;
    }

    //theoreticalRelativeFrequency-теоретическая(нормальный закон распределения) вероятность
    private double[] calculationTheoreticalRelativeFrequency(double avg, double sko, int n) {
        double[] theoreticalRelativeFrequency = new double[n];
        for (int i = 0; i < n; i++) {
            double begin = (borders.get(i) + EPSILON - avg) / sko;
            double end = (borders.get(i + 1) - avg) / sko;
            theoreticalRelativeFrequency[i] = TabularData.getF(end) - TabularData.getF(begin);
        }
        return theoreticalRelativeFrequency;
    }

    //величина расстояния кси квадрат
    private double calculationKxSquare(int n, int[] M, double[] theoreticalRelativeFrequency) {
        double Z = 0;
        for (int i = 0; i < M.length; i++) {
            Z += ((M[i] - n * theoreticalRelativeFrequency[i]) * (M[i] - n * theoreticalRelativeFrequency[i])) / (n * theoreticalRelativeFrequency[i]);
        }
        return Z;
    }

    //число вхождений в заданный интервал
    private static int frequency(double X[], double value) {
        int count = 0;
        for (double aX : X) {
            if (value >= aX) count++;
        }
        return count;
    }

    //-----------------------------------------------------------------------------------------------------
    //удаление Data из dataSet
    public void removeData(int index) {
        dataSet.remove(index);
    }

    public ArrayList<Data> getDataSet() {
        return dataSet;
    }
}
