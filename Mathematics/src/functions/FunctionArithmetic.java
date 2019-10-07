package functions;

public class FunctionArithmetic implements Fun {
    private final Fun a, b;
    private final Operator operator;

    public FunctionArithmetic(Fun a, FunctionArithmetic.Operator operator, Fun b) {
        this.a = a;
        this.operator = operator;
        this.b = b;
    }

    @Override
    public double calc(double x) {
        final double vA = a.calc(x);
        final double vB = b.calc(x);

        switch (operator) {
            case ADD:
                return vA + vB;
            case SUBTRACT:
                return vA - vB;
            case MULTIPLY:
                return vA * vB;
            case DIVIDE:
                return vA / vB;
            default:
                throw new IllegalStateException("Unexpected value: " + operator);
        }
    }

    @Override
    public Fun derivate() {
        switch (operator) {
            case ADD:
            case SUBTRACT:
                return new FunctionArithmetic(a.derivate(), operator, b.derivate());
            case MULTIPLY:
                return new FunctionArithmetic(
                        new FunctionArithmetic(a.derivate(), Operator.MULTIPLY, b),
                        Operator.ADD,
                        new FunctionArithmetic(a, Operator.MULTIPLY, b.derivate()));
            case DIVIDE:
                final FunctionArithmetic numerator = new FunctionArithmetic(
                        new FunctionArithmetic(a.derivate(), Operator.MULTIPLY, b),
                        Operator.SUBTRACT,
                        new FunctionArithmetic(a, Operator.MULTIPLY, b.derivate())
                );
                return new FunctionArithmetic(numerator, Operator.DIVIDE, new FunctionArithmetic(b, Operator.MULTIPLY, b));
            default:
                throw new IllegalStateException("Could not determine operation");
        }


    }

    enum Operator {
        ADD("+"), SUBTRACT("-"), MULTIPLY("*"), DIVIDE("/");
        final String s;

        Operator(String s) {
            this.s = s;
        }
    }
}
