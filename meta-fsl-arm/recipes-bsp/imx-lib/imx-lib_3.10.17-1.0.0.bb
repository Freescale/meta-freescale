# Copyright (C) 2013, 2014 Freescale Semiconductor

include imx-lib.inc

# FIXME: Drop 'beta' suffix for GA release
SRC_URI = "${FSL_MIRROR}/${PN}-${PV}_beta.tar.gz"
S="${WORKDIR}/${PN}-${PV}_beta"

PE = "1"

SRC_URI[md5sum] = "6c6769c2e6a2918a57a3754adcebff12"
SRC_URI[sha256sum] = "abb025eeab9e8191f4089e70716a1695dc263c5b68f42a8b28329e81889b3312"

COMPATIBLE_MACHINE = "(mx6)"
