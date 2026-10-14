# Easy use of wayland-protocols-imx by packages
#
# Points meson's dependency('wayland-protocols') to the copy carrying the
# i.MX protocols; use STAGING_DATADIR_IMX to refer to them. Other build
# systems must add ${STAGING_DATADIR_IMX}/pkgconfig to their own search path.

DEPENDS:append:imx-nxp-bsp = " wayland-protocols-imx"

PACKAGE_ARCH:imx-nxp-bsp ?= "${MACHINE_SOCARCH}"

RDEPENDS:${PN}-dev += "wayland-protocols-imx-dev"

STAGING_DATADIR_IMX = "${STAGING_DATADIR}/imx"

EXTRA_OEMESON:append:imx-nxp-bsp = " -Dpkg_config_path=${STAGING_DATADIR_IMX}/pkgconfig"

# Recipes using the i.MX protocols are NXP BSP only
COMPATIBLE_HOST ?= '(null)'
COMPATIBLE_HOST:use-nxp-bsp ?= '.*'
