# Copyright (C) 2012-2014 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-fslc.inc

PV = "3.14+git${SRCPV}"

SRCBRANCH = "patches-3.14"
SRCREV = "ac9dc67b730f3a1d10c5abbf91ed773d1e277646"

COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6)"
