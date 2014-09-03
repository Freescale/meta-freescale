# Copyright (C) 2013 Freescale Semiconductor
require imx-vpu.inc

SRC_URI += "file://obey-variables.patch"
SRC_URI[md5sum] = "1988a08687a09ef8590e66ff17ac6ed3"
SRC_URI[sha256sum] = "783f136aa9b9257d4bffbdaf05bdcb85d177c544c3f8a4674421ba7b065ed5c0"

COMPATIBLE_MACHINE = "(mx5)"
