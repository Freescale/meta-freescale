# Copyright (C) 2012-2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-fslc.inc

PV = "3.9+git${SRCPV}"

# patches-3.9
SRCREV = "e73478d010918d25986923c0b18a9805d13182f4"

COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6)"
