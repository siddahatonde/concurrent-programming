
#ifndef MATRIX_H
#define MATRIX_H

#include <iostream>
#include <vector>
#include <pthread.h>
#include <time.h>

class Matrix {

    const int MAX_THREAD_COUNT = 33;
    const int row;
    const int col;
    vector<vector<double> > data;

public:

    Matrix(const int row, const int col, bool random = false);

    Matrix(const Matrix &other);

    Matrix multiplyMatrices(const Matrix &other) const;

    Matrix multiplyMatricesMultiThreading(const Matrix &other) const;

    void printMatrices();

};

#endif