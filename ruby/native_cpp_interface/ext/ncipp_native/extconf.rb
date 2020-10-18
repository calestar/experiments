# Copyright (c) 2020 Jean-Sebastien Gelinas, see LICENSE.txt
# frozen_string_literal: true
require 'mkmf'

abort 'missing malloc()' unless have_func 'malloc'
abort 'missing free()'   unless have_func 'free'

create_makefile 'ncipp_native/ncipp_native'
