# Copyright (C) 2013 Freescale Semiconductor
# Copyright (C) 2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

# This is an alpha kernel and is not going to be used by default.
# To enable it, add: PREFERRED_VERSION_linux-imx = "3.5.7" at local.conf
DEFAULT_PREFERENCE = "-1"

DEPENDS += "lzop-native"

SRCREV = "328597018dc58c4ffa38461db09e45bef62af227"

LOCALVERSION = "-1.0.0"

COMPATIBLE_MACHINE = "(mx6)"
