# Copyright (c) 2020 Jean-Sebastien Gelinas, see LICENSE.txt
# frozen_string_literal: true

require 'ncipp'

# Quick and dirty sanity test to validate the C api
device = NCIpp::NCIppNativeDevice.new(42)

p device.get_whatever
p device.open_file "./tests/sanity.rb"
device.do_something_slow