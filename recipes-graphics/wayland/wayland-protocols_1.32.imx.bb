SUMMARY = "Collection of additional Wayland protocols"
DESCRIPTION = "Wayland protocols that add functionality not \
available in the Wayland core protocol. Such protocols either add \
completely new functionality, or extend the functionality of some other \
protocol either in Wayland core, or some other protocol in \
wayland-protocols."
HOMEPAGE = "http://wayland.freedesktop.org"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c7b12b6702da38ca028ace54aae3d484 \
                    file://stable/presentation-time/presentation-time.xml;endline=26;md5=4646cd7d9edc9fa55db941f2d3a7dc53"

SRC_URI = "git://github.com/nxp-imx/wayland-protocols-imx.git;protocol=https;branch=wayland-protocols-imx-${@oe.utils.trim_version("${PV}", 2)}"
SRCREV = "7ece577d467f8afb2f5a2f7fff3761a1e0ee9dad"

S = "${WORKDIR}/git"

UPSTREAM_CHECK_URI = "https://wayland.freedesktop.org/releases.html"

# NOTE: For i.MX drop allarch since the recipe is SOCARCH
#inherit meson pkgconfig allarch
inherit meson pkgconfig

EXTRA_OEMESON += "-Dtests=false"

PACKAGES = "${PN}"
FILES:${PN} += "${datadir}/pkgconfig/wayland-protocols.pc"

BBCLASSEXTEND = "native nativesdk"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(mx6-nxp-bsp|mx7-nxp-bsp|mx8-nxp-bsp|mx9-nxp-bsp)"
