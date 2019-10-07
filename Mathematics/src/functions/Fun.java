package functions;

public interface Fun {
    double calc(double x);

    Fun derivate();

    default Fun add(Fun other) {
        return new FunctionArithmetic(this, FunctionArithmetic.Operator.ADD, other);
    }

    default Fun subtract(Fun other) {
        return new FunctionArithmetic(this, FunctionArithmetic.Operator.SUBTRACT, other);
    }

    default Fun multiply(Fun other) {
        return new FunctionArithmetic(this, FunctionArithmetic.Operator.MULTIPLY, other);
    }

    default Fun divide(Fun other) {
        return new FunctionArithmetic(this, FunctionArithmetic.Operator.DIVIDE, other);
    }


}
