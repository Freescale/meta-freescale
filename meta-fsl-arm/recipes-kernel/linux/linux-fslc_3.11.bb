# Copyright (C) 2012-2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-fslc.inc

PV = "3.11+git${SRCPV}"

# patches-3.11
SRCREV = "ef362c02bd33a78c13ba1b4215f0a73ce3c910ff"

COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6)"
