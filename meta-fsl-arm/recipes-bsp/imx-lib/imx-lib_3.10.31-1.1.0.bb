# Copyright (C) 2013, 2014 Freescale Semiconductor

include imx-lib.inc

# FIXME: Drop 'beta' suffix for GA release
SRC_URI = "${FSL_MIRROR}/${PN}-${PV}-beta.tar.gz"
S = "${WORKDIR}/${PN}-${PV}-beta"

SRC_URI += "file://obey-variables.patch"

PE = "1"

SRC_URI[md5sum] = "0485e457eafe5a10274d171b3af79e2f"
SRC_URI[sha256sum] = "011eb34c6fd1b1ea9894bbe07a539c1aeee1500bc8fdd29d8ce1dc1d02f79e24"

COMPATIBLE_MACHINE = "(mx6)"
