/***
 * Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository
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
