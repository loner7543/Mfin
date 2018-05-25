
package model.Data;

import java.io.Serializable;

/*
 * @ author Manukhin A.V.
 */
public class Data implements Serializable {
    private double amp;
    private double[] H;

    public Data(double[] H) {
        this.H = H;
        this.amp = amp();

    }

    private double amp() {
        double max = Double.NEGATIVE_INFINITY;
        double min = Double.POSITIVE_INFINITY;
        for (double aH : H) {
            if (aH < min) min = aH;
            if (aH > max) max = aH;
        }
        return (max - min);
    }

    public double[] getH() {
        return H;
    }

    public double getAmp() {
        return amp;
    }
}
