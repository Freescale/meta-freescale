# Copyright (C) 2012-2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-fslc.inc

PV = "3.9+git${SRCPV}"

# patches-3.9
SRCREV = "10bc2b6b466ab890c3e7a6623d13b7dbea788ce4"

COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6)"
