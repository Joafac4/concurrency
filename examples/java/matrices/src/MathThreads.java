import java.util.function.Supplier;

public class MathThreads extends Thread{
    private Supplier<Double> expression;
    private double result;

    public MathThreads(Supplier<Double> expression) {
        this.expression = expression;
    }

    public void run() { result = expression.get(); }

    public double getValue() { return result; }
}
