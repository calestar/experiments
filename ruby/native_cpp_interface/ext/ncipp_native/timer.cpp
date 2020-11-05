/***
 * Copyright (c) 2020 Jean-Sebastien Gelinas, see LICENSE at the root of the repository
 ***/

#include "timer.hpp"

#include <iostream>
#include <iomanip>

TimeIt::TimeIt(std::string name) {
    this->name = name;
    clock_gettime(CLOCK_REALTIME, &begin);
}

TimeIt::~TimeIt() {
    clock_gettime(CLOCK_REALTIME, &end);
    long seconds = end.tv_sec - begin.tv_sec;
    long nanoseconds = end.tv_nsec - begin.tv_nsec;
    double elapsed = seconds + nanoseconds*1e-9;

    std::cout << "Timer (" << this->name << ") measured " << std::setprecision(10) << elapsed << " seconds." << std::endl;
}
