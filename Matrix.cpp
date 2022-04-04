

#include "Matrix.h"
#include <unistd.h>
#include <omp.h>

// Function to generate a matrix with random elements between 1 to 10
Matrix::Matrix(const int n, const int m, bool random) : row(n), col(m)
{
    // creating a neested vector to store the data as the matrix is 2D
    data = vector<vector<double> >(row);

    // populating (initializing) the vector with the value 0
    for (int i = 0; i < row; i++)
    {
        data.at(i) = vector<double>(col, 0);
    }
    if (random)
    {
        srand(time(0));
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {
                data.at(i).at(j) = rand() % 10 + 1;
            }
        }
    }
}

Matrix Matrix::multiplyMatrices(const Matrix &other) const
{
    // Matrix to save the results of the matrix and return
    Matrix C(row, other.col);
    int threadCount = (C.col * C.row) > MAX_THREAD_COUNT ? MAX_THREAD_COUNT : (C.row * C.col);

    // creating the threads
    pthread_t arr[threadCount];
    for (int i = 0; i < threadCount; i++)
    {
        vector<void *> *param = new vector<void *>({(void *)new int(i), (void *)&threadCount, (void *)this, (void *)&other, (void *)&C});
        if (pthread_create(&arr[i], nullptr, cellCalcThread, param) != 0)
            throw new runtime_error("pthread_create() error");
    }

    // waiting for all the threads to fininsh the work
    for (int i = 0; i < threadCount; i++)
        pthread_join(arr[i], nullptr);
    return C;
}

// Function to calcultate the matrix multiplication by multi threading 
Matrix Matrix::multiplyMatricesMultiThreading(const Matrix &other) const
{
    // Making the resultant matrix
    Matrix C(row, other.col);
    omp_set_num_threads((C.N * C.M) > MAX_THREAD_COUNT ? MAX_THREAD_COUNT : (C.N * C.M));
#pragma omp parallel shared(C)
    {
#pragma omp for
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < other.col; j++)
            {
                double dot = 0;
                for (int k = 0; k < col; k++)
                {
#pragma omp atomic
                    dot += data.at(i).at(k) * other.data.at(k).at(j);
                }
                C.data.at(i).at(j) = dot;
            }
        }
    }
    return C;
}


// Function to print the elements of matrices
void Matrix::printMatrices()
{
    // accessing rows one by one
    for (int i = 0; i < row; i++)
    {
        // accessing elements of rows by column one by one
        for (int j = 0; j < col; j++)
        {
            // printing the elements
            cout << data.at(i).at(j) << ' ';
        }
        cout << endl;
    }
}
