import functions.Fun;
import functions.Polynom;

public class Starter {
    public static void main(String[] args) {
        final Fun divide = new Polynom(10, 0, 1, 4, 1).divide(new Polynom(10, 0, 1, 4, 1));

        for (int i = 0; i < 10; i++) {
            System.out.println(divide.calc(i));
        }


        System.out.println(NewtonScheme.findRoot(new Polynom(6, -5, 1), 89, 1.0E-10));
    }
}
