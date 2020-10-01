# Copyright (c) 2020 Jean-Sebastien Gelinas, see LICENSE.txt
# frozen_string_literal: true

Gem::Specification.new 'nci', '0.1' do |s|
    s.summary = 'Sample Native C Interface (NCI)'
    s.author = 'Jean-Sebastien Gelinas'
    s.email = 'calestar@gmail.com'
    s.homepage = 'https://github.com/calestar/experiments'
    s.license = 'MIT'

    s.extensions = %w[ext/nci_native/extconf.rb]
    s.require_paths << 'lib'

    s.files = %w[
      Rakefile
      ext/nci_native/extconf.rb
      ext/nci_native/nci_native_device.c
      lib/nci.rb
    ]

    s.add_development_dependency('rake', '~> 12.3', '>= 12.3.3')
    s.add_development_dependency('rake-compiler', '~> 1.1.1', '>= 1.1.1')
end
