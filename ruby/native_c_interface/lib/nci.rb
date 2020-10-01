# Copyright (c) 2020 Jean-Sebastien Gelinas, see LICENSE.txt
# frozen_string_literal: true

# This module is important: the native code will inject classes here
module NCI
    # Nothing
end

# This loads the native extension
require 'nci_native/nci_native'
