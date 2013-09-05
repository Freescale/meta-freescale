# Copyright (C) 2012-2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-fslc.inc

PV = "3.11+git${SRCPV}"

# patches-3.11
SRCREV = "10b70ef71d279f2f2706abbd28f1a91e9c057c10"

COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6)"
