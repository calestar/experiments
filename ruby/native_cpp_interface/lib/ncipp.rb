# Copyright (c) 2020 Jean-Sebastien Gelinas, see LICENSE.txt
# frozen_string_literal: true

# This module is important: the native code will inject classes here
module NCIpp
    # Nothing
end

# This loads the native extension
require 'ncipp_native/ncipp_native'
