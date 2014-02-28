# Copyright (C) 2012-2014 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-fslc.inc

PV = "3.12+git${SRCPV}"

SRCBRANCH = "patches-3.12"
SRCREV = "1cd7a834347a9dcb326d14cf00cb9921f235968a"

COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6)"
