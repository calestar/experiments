# Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository
# frozen_string_literal: true

# This module is important: the native code will inject classes here
module NCIpp
    # Nothing
end

# This loads the native extension
require 'ncipp_native/ncipp_native'
