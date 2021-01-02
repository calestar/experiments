# Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository
# frozen_string_literal: true

require 'nci'

# Quick and dirty sanity test to validate the C api
device = NCI::NCINativeDevice.new(42)

p device.get_whatever
p device.open_file "./tests/sanity.rb"
p device.open_file "./bleep_bloop"
