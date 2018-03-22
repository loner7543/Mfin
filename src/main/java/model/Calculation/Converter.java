
package model.Calculation;

import model.Data.RawData;
import model.Data.Data;

public class Converter {
    //исходные данные
    private int nfi;                   //кол-во точек на сечение
    private int ns;                    //кол-во сечений
    private int fmax;                  //кол-во спектров
    private double[] rawPSI, rawTETA;

    //преременные необходимые для преобразования
    private double psi0, teta0;
    private double rAverage, rz, au, bu, cu;
    private double Y0, LFP;
    private double[] PSI, TETA, H;
    private double[] AP, BP, C;

    public Converter(RawData rawData, boolean DeflectionForGraphics) {
        //инициализация исходных данных
        nfi = rawData.getDiscretePoints();
        ns = rawData.getRadialProfilesNumber();
        Y0 = rawData.getEmitterYOffset();
        LFP = rawData.getSurfaceRadius();
        rawPSI = rawData.getXOffset();
        rawTETA = rawData.getYOffset();

        //инициализация массивов для расчета
        PSI = new double[nfi];
        TETA = new double[nfi];
        if (DeflectionForGraphics) H = new double[360];
        else H = new double[nfi];
        fmax = Math.round(nfi / 4);
        AP = new double[fmax];
        BP = new double[fmax];
        C = new double[fmax];
    }

    //создание данных-отклонений H, для дальнейшего статастического анализа
    public Data createData(boolean fWav, int minBorder, int maxBorder, int numberRadial) {
        interpol(nfi, numberRadial, rawPSI, rawTETA);
        interpol_dat();
        if (H.length == 360) {
            if (fWav) waviness(360, minBorder, maxBorder);//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
            else grannost(360, minBorder, maxBorder);
        } else {
            if (fWav) waviness(nfi, minBorder, maxBorder);//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
            else grannost(nfi, minBorder, maxBorder);
        }
        return (new Data(H.clone()));
    }

    private class Calculation {
        public double a, b, e;
        public double t2;
        public double sn, cs, sinp, cosp, sind, cosd;

        public Calculation(double y0, double l, double psi, double teta, double r) {
            a = r / l;
            t2 = teta * teta;
            sn = y0 / r;
            cs = Math.sqrt(1.0 - sn * sn);
            sinp = Math.sin(psi);
            cosp = Math.cos(psi);
            sind = sinp * cs - cosp * sn;
            cosd = cosp * cs + sinp * sn;
            b = Math.sqrt(1 + a * a - 2 * a * cosd + t2);
            e = sinp - a * sn;
        }
    }

    //____________________________________________________INTERPOL__________________________________________________
    private void interpol(int n, int ns, double[] p, double[] t) {
        int m = n * (ns - 1);        //определяем позицию с которой начинается отсчет для сечения с номером ns
        psi0 = 0;
        teta0 = 0;
        for (int i = 0; i < n; i++) //считываем psi и teta для выбранного номера сечения
        {
            PSI[i] = p[m + i];
            psi0 += PSI[i];
            TETA[i] = t[m + i];
            teta0 += TETA[i];
        }
        psi0 /= n;              //среднее значение из PSI[]
        teta0 /= n;             //среднее значение из TETA[]
        for (int i = 0; i < n; i++) {
            PSI[i] -= psi0;      //отклонение от среднего значения
            TETA[i] -= teta0;
        }
        rAverage = averageRadius(Y0, LFP, psi0, teta0);
        rz = dR_dz(1000.0 * Y0, 1000.0 * LFP, psi0, teta0, 1000.0 * rAverage);
        au = coef_A(1000.0 * Y0, 1000.0 * LFP, psi0, teta0, 1000.0 * rAverage);       //вычисление коэф диф уравнения
        bu = coef_B(1000.0 * Y0, 1000.0 * LFP, psi0, teta0, 1000.0 * rAverage);
        cu = coef_C(1000.0 * Y0, 1000.0 * LFP, psi0, teta0, 1000.0 * rAverage);
    }

    private double averageRadius(double y0, double l, double psiGR, double tetaGR) {
        double eps = 0.0001;
        double rad = 2 * Math.PI / 360;             //для перевода из градусов в радианы
        double psi = rad * psiGR;
        double teta = Math.tan(rad * tetaGR);
        double r1 = y0;
        double r2 = l;
        double rs = (r1 + r2) / 2;
        double err = Math.abs(r2 - r1);
        double y1, y2, ys;
        while (err > eps) {
            y1 = Hl_R(y0, l, psi, teta, r1);
            y2 = Hl_R(y0, l, psi, teta, r2);
            ys = Hl_R(y0, l, psi, teta, rs);
            if ((y1 * ys) < 0) r2 = rs;
            if ((ys * y2) < 0) r1 = rs;
            rs = (r1 + r2) / 2;
            err = Math.abs(r2 - r1);
        }
        return rs;
    }

    private double Hl_R(double y0, double l, double psi, double teta, double r) {
        Calculation cal = new Calculation(y0, l, psi, teta, r);
        return ((cal.cosd - cal.a - cal.b * cal.cs) * cal.e + cal.t2 * cal.sn);
    }

    private double dR_dz(double y0, double l, double psiGR, double tetaGR, double rAv) {
        double rad = 2 * Math.PI / 360;          //для перевода в радианы
        double psi = rad * psiGR;
        double teta = Math.tan(rad * tetaGR);
        Calculation cal = new Calculation(y0, l, psi, teta, rAv);
        return (-teta * (cal.b - cal.cosp + cal.a * cal.cs) / ((cal.sind + cal.b * cal.sn) * cal.e + cal.cs * cal.t2));
    }

    //коэффициенты диф. уравнения
    private double coef_A(double y0, double l, double psiGR, double tetaGR, double rAv) {
        double dx = 0.0001;
        double f1 = dH_dl(y0, l, psiGR, tetaGR, rAv - dx);
        double f2 = dH_dl(y0, l, psiGR, tetaGR, rAv + dx);
        return (rAv * (f2 - f1) / (dx + dx));
    }

    private double coef_B(double y0, double l, double psiGR, double tetaGR, double rAv) {
        double dx = 0.0001;
        double f1 = dH_dl(y0, l, psiGR - dx, tetaGR, rAv);
        double f2 = dH_dl(y0, l, psiGR + dx, tetaGR, rAv);
        return (rAv * (f2 - f1) / ((dx + dx) * 2 * Math.PI / 360));
    }

    private double coef_C(double y0, double l, double psiGR, double tetaGR, double rAv) {
        double dx = 0.0001;
        double f1 = dH_dl(y0, l, psiGR, tetaGR - dx, rAv);
        double f2 = dH_dl(y0, l, psiGR, tetaGR + dx, rAv);
        return (rAv * (f2 - f1) / ((dx + dx) * 2 * Math.PI / 360));
    }

    private double dH_dl(double y0, double l, double psiGR, double tetaGR, double rAv) {
        double rad = 2 * Math.PI / 360;           //для перевода в радианы
        double psi = rad * psiGR;
        double teta = Math.tan(rad * tetaGR);
        Calculation cal = new Calculation(y0, l, psi, teta, rAv);
        double d = cal.cosd - cal.a - cal.b * cal.cs;
        double f = cal.sind + cal.b * cal.sn;
        return ((d * cal.e + cal.sn * cal.t2) / (f * cal.e + cal.cs * cal.t2));
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~INTERPO~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //_____________________________________INTERPOL_DAT____________________________________________________

    private void interpol_dat() {
        for (int i = 0; i < nfi; i++) {
            PSI[i] = (bu * PSI[i] + cu * TETA[i]) * 2 * Math.PI / 360;
        }
        for (int i = 0; i < fmax; i++) {
            coefft(nfi, PSI, i);
        }
        vol_fft(fmax);

    }

    private double coefft(int n, double[] y, int k) {
        double ak = 0;
        double bk = 0;
        double t = 2 * Math.PI * k / n;
        for (int i = 0; i < n; i++) {
            ak += y[i] * Math.cos(t * i);
            bk += y[i] * Math.sin(t * i);
        }
        ak = ak * 2 / n;
        bk = bk * 2 / n;
        AP[k] = ak;
        BP[k] = bk;
        return (Math.sqrt(ak * ak + bk * bk));
    }

    private double vol_fft(int fmax) {
        double dk, ak, bk, s;
        for (int k = 0; k < fmax; k++) {
            dk = (k + 1);
            s = 1 / (au * au + dk * dk);
            ak = AP[k];
            bk = BP[k];
            AP[k] = -s * (au * ak + dk * bk);
            BP[k] = s * (dk * ak - au * bk);
            C[k] = Math.sqrt(AP[k] * AP[k] + BP[k] * BP[k]);
        }
        s = 0.0001;
        for (int k = 0; k < fmax; k++) {
            if (C[k] > s) s = C[k];
        }
        for (int k = 0; k < fmax; k++) C[k] /= s;
        return s;
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~INTERPOL_DAT~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //_________________________________________POL_FFT(формирует массив H)___________________________________________________________
    private void pol_fft(int n, int m1, int m2) {
        if (AP.length < m1) m1 = 0;
        if (AP.length < m2) m2 = AP.length;
        double dx, t, x;
        dx = 2 * Math.PI / n;
        for (int i = 0; i < n; i++) {
            x = dx * i;
            H[i] = 0;
            for (int k = m1; k < m2; k++) {
                t = (k + 1) * x;
                H[i] += AP[k] * Math.cos(t) + BP[k] * Math.sin(t);
            }
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~POL_FFT~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private void grannost(int n, int grminv, int grmaxv) {
        pol_fft(n, grminv, grmaxv);
    }

    private void waviness(int n, int wminv, int wmaxv) {
        pol_fft(n, wminv, wmaxv);
    }
}
