import java.time.Duration;
import java.time.Instant;

public class Main {

    public static void main(String[] args) {
        Matrix A = Matrix.random(5000,50);
        Matrix B = Matrix.random(50,1000);

        Instant startTime = Instant.now();
        Matrix C = A.multiThreadMultiplication(B);
        Instant endTime = Instant.now();
        long multiThreadTime = Duration.between(startTime, endTime).toMillis();

        startTime = Instant.now();
        Matrix D = A.multiplicationOneThread(B);
        endTime = Instant.now();
        long oneThreadTime = Duration.between(startTime, endTime).toMillis();
        System.out.println("----------\nCheck Matrix Equal = " + D.equals(C));

        System.out.println("Elapsed time of matrix multiplication using multi threading = " + multiThreadTime);
        System.out.println("Elapsed time of matrix multiplication using one thread = " + oneThreadTime);
        System.out.println("----------");

        System.out.println("Only printed 10 X 10 of every matrix\n");

        System.out.println("----MATRIX A------\n");
        Matrix.printMatrix(A);
        System.out.println("----MATRIX B------\n");
        Matrix.printMatrix(B);
        System.out.println("----RESULTANT MATRIX------\n");
        Matrix.printMatrix(C);

    }
}
