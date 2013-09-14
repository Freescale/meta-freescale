# Copyright (C) 2012-2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-fslc.inc

PV = "3.11+git${SRCPV}"

# patches-3.11
SRCREV = "a318c1dd398d8cb44e98964825ab038010793142"

COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6)"
