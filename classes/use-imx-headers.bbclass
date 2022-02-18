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

DEPENDS:append:imx-nxp-bsp = " linux-imx-headers"

# Set runtime dependency of -dev for package inheriting this class to
# linux-imx-headers-dev package. This is required in order to propagate
# headers into the SDK
RDEPENDS:${PN}-dev += "linux-imx-headers-dev"

PACKAGE_ARCH:imx-nxp-bsp ?= "${MACHINE_SOCARCH}"

STAGING_INCDIR_IMX = "${STAGING_INCDIR}/imx"

# Recipes that inherit this class are contracted to use NXP BSP only.
# This is done by overriding the COMPATIBLE_HOST, as this would effectively
# cause recipes to be skipped in case if 'use-nxp-bsp' override is not
# defined for them. This effectively marks recipes that should only be
# built using NXP BSP, and gives an indication to mainline BSP creators
# that recipe is not compatible with mainline.
#
# Typical example here would be imx-vpu-hantro recipe, which requires NXP
# BSP and is not compatible with mainline.
COMPATIBLE_HOST = '(null)'
COMPATIBLE_HOST:use-nxp-bsp = '.*'
