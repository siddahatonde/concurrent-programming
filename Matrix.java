

import java.util.ArrayList;

public class Matrix {

    private final int row;
    private final int col;
    private final double[][] data;

    public Matrix(int row, int col) {
        this.row = row;
        this.col = col;
        data = new double[row][col];
    }

    /**
     * @param row is the size of row of the matrix to be generated
     * @param col is the size of column of the matrix to be generated
     * @return Matrix obj itself after creating random elements in a matrix
     */
    public static Matrix random(int row, int col) {
        Matrix randomMatrix = new Matrix(row, col);
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                randomMatrix.data[i][j] = (int) (Math.random() * 10);
        return randomMatrix;
    }

    /**
     * @param A is the matrix to be multiplied
     * @return the results of multiplication
     */
    public Matrix multiThreadMultiplication(Matrix A) {
        // check condition of matrix multiplication
        if (this.col != A.row)
            throw new RuntimeException("Multiplication impossible due to different matrix dimensions");

        // Making the matrix of the size of current instance of class
        Matrix C = new Matrix(this.row, A.col);

        // Using 150 Threads to multiply
        int MAX_THREAD_COUNT = 150;

        int threadCount = Math.min((C.row * C.col), MAX_THREAD_COUNT);
        
        ArrayList<Thread> threads = new ArrayList<>(threadCount);
        
        int div = (C.row * C.col) / threadCount;
        int mod = (C.row * C.col) % threadCount;
        int first = 0;
        for (int i = 0; i < threadCount; i++) {
            int last = first + div;
            if (mod > 0) {
                last++;
                mod--;
            }
            MyThread r = new MyThread(this.getData(), A.getData(), C, first, last);
            Thread t = new Thread(r, "Calculating");
            threads.add(t);
            t.start();
            first = last;
        }

        // waiting for all the threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {
            }
        }
        return C;
    }

    /**
     * @param m1 is the matrix (instance of class Matrix) passed into matrix class
     *           which will be multiplied by the instance of this class Matrix
     * @return the resultant matrix of m1 and the current matrix object of the class
     */
    public Matrix multiplicationOneThread(Matrix m1) {
        if (this.col != m1.row) throw new RuntimeException("Multiplication impossible due to different matrix dimensions");
        Matrix m2 = new Matrix(this.row, m1.col);
        for (int i = 0; i < this.row; i++)
            for (int j = 0; j < m1.col; j++)
                for (int k = 0; k < this.col; k++)
                    m2.data[i][j] += (this.data[i][k] * m1.data[k][j]);
        return m2;
    }


    /**
     * Access function to get the 2D array matrix
     * @return the 2D array
     */
    public double[][] getData() {
        return data;
    }

    /**
     * Modifier function to update the size of matrix and the elements of matrix itself
     * @param row is the parameter to update the row size of matrix
     * @param col is the parameter to update the col size of matrix
     * @param data is the parameter to change the elements of matrix itself by new matrix
     */
    public void setData(int row, int col, double data) {
        if (row > this.row || col > this.col) throw new RuntimeException("Wrong dimensions. Failed to set data");
        this.data[row][col] = data;
    }

    public static void printMatrix(Matrix printMatrix) {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++)
                System.out.print(printMatrix.getData()[i][j] + " ");
            System.out.println();
        }

    }

    /**
     * @param obj is the instance of matrix which the matrix will be compared to check
     * @return true if both current instance of matrix and passed instance of matrix are equal otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        Matrix castMatrix = (Matrix) obj;

        // if the size of both matrices are different, return false
        if (castMatrix.row != this.row || castMatrix.col != this.col)
            return false;
        // Checking the elements of both matrices
        // if at any point passed matrix element is not equal to current matrix element, it returns false
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++)
                if (castMatrix.getData()[i][j] != this.getData()[i][j])
                    return false;
        }
        return true;
    }

}