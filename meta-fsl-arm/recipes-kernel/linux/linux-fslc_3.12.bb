# Copyright (C) 2012-2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-fslc.inc

PV = "3.12+git${SRCPV}"

# patches-3.12
SRCREV = "83ccff683f04f0f4e47bce191d6ffde52fc79e13"

COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6)"
