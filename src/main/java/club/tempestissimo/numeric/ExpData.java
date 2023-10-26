package club.tempestissimo.numeric;

/**
 * A*10^N
 */
public class ExpData {
    public double A;
    public int N;

    public ExpData add(ExpData data){
        int nStep = this.N - data.N;
            double deltaA = A - data.A *Math.pow(10, -nStep);
            return new ExpData(deltaA, this.N);
    }


    public ExpData(double A, int N){
        this.A = A;
        this.N = N;
    }

}
