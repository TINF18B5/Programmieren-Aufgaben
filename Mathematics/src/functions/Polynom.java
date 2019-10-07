package functions;

public class Polynom implements Fun {
    private final double[] coefficients;

    public Polynom(double... coefficients) {
        this.coefficients = coefficients;
    }

    @Override
    public double calc(double x) {
        double out = 0;
        for (int i = 0; i < coefficients.length; i++) {
            out += coefficients[i] * Math.pow(x, i);
        }
        return out;
    }

    @Override
    public Fun derivate() {
        double[] newCoeff = new double[this.coefficients.length - 1];
        for (int i = 1; i < this.coefficients.length; i++) {
            newCoeff[i - 1] = i * this.coefficients[i];
        }
        return new Polynom(newCoeff);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < coefficients.length; i++) {
            if (i != 0)
                sb.append(" + ");

            sb.append(coefficients[i]);
            sb.append(" * x ^ ");
            sb.append(i);
        }
        return sb.toString();
    }
}
