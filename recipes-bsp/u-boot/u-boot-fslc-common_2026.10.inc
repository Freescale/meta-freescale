# Copyright (C) 2012-2022 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

inherit fsl-u-boot-localversion

HOMEPAGE = "https://github.com/Freescale/u-boot-fslc"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"

DEPENDS += "bison-native flex-native"

PV = "2026.10+fslc+git${SRCPV}"

SRC_URI = "git://github.com/Freescale/u-boot-fslc.git;branch=${SRCBRANCH};protocol=https \
           file://0001-Add-support-for-OpenSSL-Provider-API.patch \
"

SRCREV = "322904a1db22d45b9a7f4627e1093fe4f6befaba"
SRCBRANCH = "2026.10+fslc"

CVE_PRODUCT = "denx:u-boot"

B = "${WORKDIR}/build"
