# Copyright (C) 2013 Timesys Corporation
# Released under the MIT license (see COPYING.MIT for the terms)
include linux-imx.inc

# Revision of 3.0.15_vybrid branch
SRC_URI = "git://github.com/Timesys/linux-timesys.git \
           file://defconfig \
           file://0001-mvf_fec.c-Fix-mac-address-read-to-match-fuse-layout-.patch \
           file://0002-mvf.c-Change-console-device-name-to-match-mainline-u.patch \
"

SRCREV = "dea13473ce0c106fc56af798eefc7196bb150695"
LOCALVERSION = "-3.0-mvf+yocto"

COMPATIBLE_MACHINE = "(vf60)"
