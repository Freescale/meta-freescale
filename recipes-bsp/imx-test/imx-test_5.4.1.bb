# Copyright (C) 2013-2016 Freescale Semiconductor

include imx-test.inc

SRC_URI[md5sum] = "fa1a5cdcfbdd6a3fed0ab4a5fd1f97bc"
SRC_URI[sha256sum] = "997ec10bdc6991e9687a2c2fa5816bb50b08d73bcfe4093f988a7cac4bf0b06a"

SRC_URI += "file://0001-imx-test-Fix-Makefiles-to-handle-library-dependencie.patch"

PR = "r1"

COMPATIBLE_MACHINE = "(mx6|mx6ul|mx7)"
