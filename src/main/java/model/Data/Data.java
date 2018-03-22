
package model.Data;

import java.io.Serializable;

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
        for (int i = 0; i < H.length; i++) {
            if (H[i] < min) min = H[i];
            if (H[i] > max) max = H[i];
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
