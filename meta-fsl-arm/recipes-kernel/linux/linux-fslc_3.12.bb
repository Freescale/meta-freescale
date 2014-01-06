# Copyright (C) 2012-2014 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-fslc.inc

PV = "3.12+git${SRCPV}"

SRCBRANCH = "patches-3.12"
SRCREV = "9ab58339c5e84c34df82f8a2f68b8b033b9bd03f"

COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6)"
