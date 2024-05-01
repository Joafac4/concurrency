import java.util.function.Supplier;


public class MatrixParallelThread extends Thread{
    private Supplier<double[]> expression;
    private double[] result;

    public MatrixParallelThread(Supplier<double[]> expression) {
        this.expression = expression;
    }

    public void run() { result = expression.get(); }

    public double[] getValue() { return result; }
}




