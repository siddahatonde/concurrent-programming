
public class MyThread extends Thread {
    private final double[][] m1;
    private final double[][] m2;
    private static Matrix C;
    private final int row;
    private final int col;

    MyThread(double[][] m1, double[][] m2, Matrix resultMat, int first, int last) {
        this.m1 = m1;
        this.m2 = m2;
        C = resultMat;
        row = first;
        col = last;
    }

    public void run() {
        for (int j = row; j < col; j++) {
            int col = j / C.getData()[0].length;
            int row = j % C.getData()[0].length;
            for (int k = 0; k < m1[0].length; k++) {
                if (C.getData()[col][row] != 0)
                    C.setData(col, row, C.getData()[col][row] + (m1[col][k] * m2[k][row]));
                else
                    C.setData(col, row, m1[col][k] * m2[k][row]);
            }
        }
    }
}
