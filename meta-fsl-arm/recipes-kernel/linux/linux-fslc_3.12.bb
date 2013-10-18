# Copyright (C) 2012-2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-fslc.inc

PV = "3.12+git${SRCPV}"

# patches-3.12
SRCREV = "83cc0743f5cbcf1f741867d5c193b09dd9be1adf"

COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6)"
