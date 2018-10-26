# Easy use of linux-imx-headers by packages
#
# This allow to easy reuse of binary packages among similar SoCs. The
# usual use for this is to share SoC specific packages among different
# boards independently of the kernel version it is using, as far it is
# ABI compatible with the official version it will just work.
#
# All recipes using this class ought to depend on linux-imx-headers
# and by default to use MACHINE_SOCARCH.
#
# Please use the STAGING_INCDIR_IMX variable to refer to the installed
# headers.
#
# Copyright 2018 (C) O.S. Systems Software LTDA.

DEPENDS_append_imx = " linux-imx-headers"
PACKAGE_ARCH_imx ?= "${MACHINE_SOCARCH}"

STAGING_INCDIR_IMX = "${STAGING_INCDIR}/imx"
