# Copyright (C) 2012-2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-fslc.inc

PV = "3.9+git${SRCPV}"

# patches-3.9
SRCREV = "3a1e70d10297f92525eee3bd35aae7ecab201c39"

COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6)"
