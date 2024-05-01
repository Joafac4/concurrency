public class Matrix {
    private final double[][] values;

    public Matrix(double[][] values) {
        this.values = values;
    }

    public double[][] getValues() {
        return values;
    }

    public double sum() {
        var result = 0.0;
        for (double[] value : values) {
            result += addRow(value);
        }
        return result;
    }

    public double sumParallel()  {
        var threads = new Thread[values.length];
        for( int i = 0; i < values.length; i++){
            int j = i;
            var thread = new MathThreads(() -> addRow(values[j]));
            threads[i] = thread;
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        var result = 0.0;
        for (Thread thread : threads) {
            var t = (MathThreads) thread;
            result += t.getValue();
        }
        return result;
    }

    public Matrix addSerial(Matrix other) {
        double[][] result = new double[values.length][];
        for (int i = 0; i < values.length; i++) {
            int cols = values[i].length;
            var row = new double[cols];
            for (int j = 0; j < cols; j++) {
                row[j] = values[i][j] + other.values[i][j];
            }
            result[i] = row;
        }
        return new Matrix(result);
    }
    public Matrix addParallel(Matrix other){
        var threads = new MatrixParallelThread[values.length];
        for (int i = 0; i < threads.length; i++){
            int j = i;
            MatrixParallelThread thread = new MatrixParallelThread(() -> addRow2(values[j],other.values[j]));
            thread.start();
            threads[i] = thread;
        }
        for (int i = 0; i < threads.length; i++){
            try{
                MatrixParallelThread thread = threads[i];
                thread.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        double[][] result = new double [values.length][values.length];
        for (int i = 0; i < result.length; i ++){
            result[i] = threads[i].getValue();
        }
        return new Matrix(result);
    }

    private double addRow(double[] value) {
        var result = 0.0;
        for (double v : value) {
            result += v;
        }
        return result;
    }

    private double[] addRow2(double[] value, double[] other) {
        var result = new double[value.length];
        for (int i = 0; i < value.length; i++) {
            result[i] = value[i] + other[i];
        }
        return result;
    }


    public boolean checkEquals( double[][] other){
        if (values.length != other.length) return false;
        for (int i = 0; i < values.length; i++){
            if (values[i].length != other[i].length) return false;
            for (int j = 0; j < values[i].length; j++){
                if (values[i][j] != other[i][j]) return false;
            }
        }
        return true;
    }

    public void printMtxValues(){
        for (int i = 0; i < values.length; i++){
            for (int j = 0; j < values[i].length; j++){
                System.out.print(values[i][j] + " ");
            }
            System.out.println();
        }
    }





}