# Copyright (C) 2013-2014 Timesys Corporation
# Released under the MIT license (see COPYING.MIT for the terms)
include linux-imx.inc

SUMMARY = "Linux Kernel with added drivers and board support for Vybrid-based platforms"

# Revision of 3.0.15_vybrid branch
SRC_URI = "git://github.com/Timesys/linux-timesys.git;protocol=git;branch=${SRCBRANCH} \
           file://defconfig \
"

SRC_URI_append_twr-vf65gs10 = "file://0001-mvf_fec.c-Fix-mac-address-read-to-match-fuse-layout-.patch \
           file://0002-mvf.c-Change-console-device-name-to-match-mainline-u.patch \
"

SRCBRANCH = "3.0.15_vybrid-twr"
SRCREV = "50c4c848d6b8743894cfcec166db475ef6140504"
LOCALVERSION ?= "-${SRCBRANCH}"

COMPATIBLE_MACHINE = "(vf60)"
