# Copyright (C) 2011-2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-imx.inc

PR = "${INC_PR}.11"

COMPATIBLE_MACHINE = "(mx6)"

# Revision of 1.1.0 tag
SRCREV = "c27cb3851bb6f822f8a92e4a1e10fba19284bdd4"
LOCALVERSION = "-1.1.0+yocto"

# Revision of 3.0.0 tag
SRCREV_mx6sl = "7e8c89cd4b47c4ac6ec6a91a5f54d450688bde4f"
LOCALVERSION_mx6sl = "-3.0.0+yocto"

SRCREV_mx6dl = "7e8c89cd4b47c4ac6ec6a91a5f54d450688bde4f"
LOCALVERSION_mx6dl = "-3.0.0+yocto"

SRC_URI += "file://fix_getrusage_for_perf.patch \
            file://egalax_ts-Add-support-for-single-touch-in-Kconfig.patch \
           "

# iMX6Q SabreLITE changes done by Boundary Devices
SRC_URI_append_imx6qsabrelite = " file://sync-boundary-changes.patch"
