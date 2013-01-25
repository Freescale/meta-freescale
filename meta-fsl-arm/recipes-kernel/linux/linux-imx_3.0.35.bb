# Copyright (C) 2011-2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-imx.inc

PR = "${INC_PR}.10"

COMPATIBLE_MACHINE = "(mx6)"

# Revision of 12.09.01 tag
SRCREV = "059685632ebd37a89e60cbb1e6dd535b3b08c3be"
LOCALVERSION = "-12.09.01+yocto"

SRC_URI += "file://fix_getrusage_for_perf.patch \
            file://egalax_ts-Add-support-for-single-touch-in-Kconfig.patch \
           "

# iMX6Q SabreLITE changes done by Boundary Devices
SRC_URI_append_imx6qsabrelite = " file://sync-boundary-changes.patch"
