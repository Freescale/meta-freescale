# Copyright (C) 2012-2013 Freescale Semicondutors
# Released under the MIT license (see COPYING.MIT for the terms)

LIC_FILES_CHKSUM = "file://EULA.txt;md5=ea4d5c069d7aef0838a110409ea78a01"

require libfslparser.inc

PR = "${INC_PR}.0"

SRC_URI[md5sum] = "9fd8105530e1668ae91bd53a5b0d9807"
SRC_URI[sha256sum] = "d3139e28e453d2af04439e607cd12ad17e117144049c9a8add05a5a142c654ae"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
