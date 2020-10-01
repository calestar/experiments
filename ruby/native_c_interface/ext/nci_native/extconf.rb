# Copyright (c) 2020 Jean-Sebastien Gelinas, see LICENSE.txt
# frozen_string_literal: true
require 'mkmf'

abort 'missing malloc()' unless have_func 'malloc'
abort 'missing free()'   unless have_func 'free'

abort('missing "gpiod.h"') unless find_header('gpiod.h', ['/usr/include'])
abort('missing libgpiod') unless find_library('gpiod', 'gpiod_chip_open_lookup')

create_makefile 'nci_native/nci_native'
