# Copyright (c) 2020 Jean-Sebastien Gelinas, see LICENSE.txt
# frozen_string_literal: true

require 'nci'

# Quick and dirty sanity test to validate the C api
device = NCI::NCINativeDevice.new(42)

p device.get_whatever
p device.open_file "./tests/sanity.rb"
p device.open_file "./bleep_bloop"
