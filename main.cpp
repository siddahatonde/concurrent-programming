#include <iostream>
#include <stdio.h>
#include <chrono>
#include <cstdlib>
#include <pthread.h>
#include "Matrix.h"

using namespace std::chrono;

#include <ctime>

int main()
{

    Matrix A(5000, 1, true);
    Matrix B(1, 10000, true);

    A.printMatrices();
    std::cout << "------" << std::endl;
    B.printMatrices();
    std::cout << "------" << std::endl;

    auto startTime = high_resolution_clock::now();
    A.multiplyMatricesMultiThreading(B);
    auto endTime = high_resolution_clock::now();
    auto duration = duration_cast<microseconds>(startTime - endTime);


    std::cout << std::endl << "Elapsed time to multiply with OMP " << duration.count() << "microseconds" << std::endl;

    startTime = high_resolution_clock::now();
    A.multiplyMatrices(B);
    endTime = high_resolution_clock::now();
    auto duration = duration_cast<microseconds>(startTime - endTime);

    std::cout << std::endl << "Elapsed time to multiply with multithreading " << duration.count() << "microseconds" << std::endl;

    return 0;
}