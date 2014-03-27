# Copyright (C) 2014 O.S. Systems Software LTDA.
SUMMARY = "Produces a Manufacturing Tool compatible Linux Kernel"
DESCRIPTION = "Linux Kernel provided and supported by Freescale that produces a \
Manufacturing Tool compatible Linux Kernel to be used in updater environment"

require linux-imx_${PV}.bb
require linux-mfgtool.inc
