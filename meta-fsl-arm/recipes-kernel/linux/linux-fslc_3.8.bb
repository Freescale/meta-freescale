# Copyright (C) 2012-2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-fslc.inc

PV = "3.8+git${SRCPV}"
PR = "r5"

# patches-3.8
SRCREV = "8a197c14157aca439ce104e4bf6cd12076f81630"

COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6)"
