# Copyright (C) 2011-2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-imx.inc

PR = "${INC_PR}.1"

COMPATIBLE_MACHINE = "(mx6)"

# Revision of 12.02.01 tag
SRCREV = "1e54cdcdb6940a58d5e09174410fe0fe28041fa3"
LOCALVERSION = "-12.02.01+yocto-${DATE}"
