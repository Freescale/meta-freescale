# Copyright (C) 2014 O.S. Systems Software LTDA.
# Copyright (C) 2014-2016 Freescale Semiconductor
# Copyright 2017-2019 NXP

FILESEXTRAPATHS:prepend := "${THISDIR}/u-boot-imx:"

require u-boot-imx_${PV}.bb
require u-boot-mfgtool.inc
