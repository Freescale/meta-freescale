# Copyright (C) 2011-2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-imx.inc

PR = "${INC_PR}.14"

COMPATIBLE_MACHINE = "(mx6)"

# Revision of 1.1.0 branch
SRCREV = "21304e170e6aa140d189158fcf27d731d3547969"
LOCALVERSION = "-1.1.0+yocto"

# Revision of 12.10.02 branch
SRCREV_mx6sl = "741f7dcf5dd1b5ce82986eda139ddbce69c66e31"
LOCALVERSION_mx6sl = "-12.10.02+yocto"

# Revision of 3.0.0 branch
SRCREV_mx6dl = "7e8c89cd4b47c4ac6ec6a91a5f54d450688bde4f"
LOCALVERSION_mx6dl = "-3.0.0+yocto"

SRC_URI += "file://fix_getrusage_for_perf.patch \
            file://egalax_ts-Add-support-for-single-touch-in-Kconfig.patch \
           "
