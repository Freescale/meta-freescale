# Copyright (C) 2012-2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-fslc.inc

PV = "3.12+git${SRCPV}"

# patches-3.12
SRCREV = "928c8b1f048424c90960c9c6275b3b34a850fe16"

COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6)"
