# Copyright (C) 2012-2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-fslc.inc

PV = "3.8+git${SRCPV}"
PR = "r5"

# patches-3.8
SRCREV = "7fd77a4db4dc59f98a7118fdbbf02d82b03de1de"

COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6)"
