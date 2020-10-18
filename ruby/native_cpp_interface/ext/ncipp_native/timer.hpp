/***
 * Copyright (c) 2020 Jean-Sebastien Gelinas, see LICENSE.txt
 ***/

#pragma once

#include <string>


class TimeIt {
    struct timespec begin, end;
    std::string name;

public:
    TimeIt(std::string name);

    ~TimeIt();
};
