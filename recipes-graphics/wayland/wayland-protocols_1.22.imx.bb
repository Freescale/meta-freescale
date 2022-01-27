SUMMARY = "Collection of additional Wayland protocols"
DESCRIPTION = "Wayland protocols that add functionality not \
available in the Wayland core protocol. Such protocols either add \
completely new functionality, or extend the functionality of some other \
protocol either in Wayland core, or some other protocol in \
wayland-protocols."
HOMEPAGE = "http://wayland.freedesktop.org"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=c7b12b6702da38ca028ace54aae3d484 \
                    file://stable/presentation-time/presentation-time.xml;endline=26;md5=4646cd7d9edc9fa55db941f2d3a7dc53"

SRC_URI = "git://source.codeaurora.org/external/imx/wayland-protocols-imx.git;protocol=https;branch=wayland-protocols-imx-1.22"
SRCREV = "0f71ffe59cf4c3f050ff79ea18f96345fcf0061e"
S = "${WORKDIR}/git"

inherit autotools pkgconfig

PACKAGES = "${PN}"
FILES:${PN} += "${datadir}/pkgconfig/wayland-protocols.pc"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(imxfbdev|imxgpu)"
