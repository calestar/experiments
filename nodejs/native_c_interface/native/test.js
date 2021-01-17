/***
 * Copyright (c) 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository
 ***/
var {nci} = require('./index.js');

let device = new nci.NCINativeDevice(12);

console.log(device.get_whatever());
console.log(device.open_file("./.nvmrc"));
console.log(device.open_file("./bleep_bloop"));
