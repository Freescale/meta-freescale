# Copyright (C) 2013 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

DEFAULT_PREFERENCE = "-1"

# 3.10.9-1.0.0 rc1 commit
SRCREV = "dbf364b3d0123e5328ae28455c57e588635232c7"
LOCALVERSION = "-1.0.0_alpha"

COMPATIBLE_MACHINE = "(mx6)"
