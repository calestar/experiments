# Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository
# frozen_string_literal: true

require 'ncipp'

# Quick and dirty sanity test to validate the C api
device = NCIpp::NCIppNativeDevice.new(42)

p device.get_whatever
p device.open_file "./tests/sanity.rb"
device.do_something_slow