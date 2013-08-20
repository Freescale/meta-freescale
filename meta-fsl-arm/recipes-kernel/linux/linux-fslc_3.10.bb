# Copyright (C) 2012-2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-fslc.inc

PV = "3.10+git${SRCPV}"

# patches-3.10
SRCREV = "9cb40913a68945ab653b58abdf5949c68dd85210"

COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6)"
