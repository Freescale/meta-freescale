# Copyright (C) 2012-2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-fslc.inc

PV = "3.12+git${SRCPV}"

# patches-3.12
SRCREV = "1b62e41916cf33e4d3fc3d61564f1f5a0f7a0d2e"

COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6)"
