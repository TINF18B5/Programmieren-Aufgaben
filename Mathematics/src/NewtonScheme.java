import functions.Fun;

public class NewtonScheme {

    public static double findRoot(Fun function, double start, double threshHold) {
        int count = 1;
        final Fun derivated = function.derivate();

        double x = start;
        double x1 = x - (function.calc(x) / derivated.calc(x));

        while (Math.abs(x1 - x) > threshHold) {
            count++;
            x = x1;
            x1 = x - (function.calc(x) / derivated.calc(x));

            if (count % 10 == 0) {
                System.out.println(count + " <--> " + x1);
            }
        }

        return x1;
    }
}
