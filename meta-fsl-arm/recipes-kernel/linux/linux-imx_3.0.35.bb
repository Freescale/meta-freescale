# Copyright (C) 2011-2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-imx.inc

PR = "${INC_PR}.1"

COMPATIBLE_MACHINE = "(mx6)"

# Revision of 12.09.01 tag
SRCREV = "eaaf30efdc8dfeb03418bde1499a76c9903bd211"
LOCALVERSION = "-12.09.01+yocto"

SRC_URI += "file://perf_tools_Fix_build_against_newer_glibc.patch"

