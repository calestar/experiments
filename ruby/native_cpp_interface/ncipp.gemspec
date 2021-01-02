# Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE.txt
# frozen_string_literal: true

Gem::Specification.new 'ncipp', '0.1' do |s|
    s.summary = 'Sample Native C++ Interface (NCIpp)'
    s.author = 'Jean-Sebastien Gelinas'
    s.email = 'calestar@gmail.com'
    s.homepage = 'https://github.com/calestar/experiments'
    s.license = 'MIT'

    s.extensions = %w[ext/nci_native/extconf.rb]
    s.require_paths << 'lib'

    s.files = %w[
      Rakefile
      ext/ncipp_native/extconf.rb
      ext/ncipp_native/nci_native_device.cpp
      ext/ncipp_native/timer.cpp
      ext/ncipp_native/timer.hpp
      lib/ncipp.rb
    ]

    s.add_development_dependency('rake', '~> 12.3', '>= 12.3.3')
    s.add_development_dependency('rake-compiler', '~> 1.1.1', '>= 1.1.1')
end
