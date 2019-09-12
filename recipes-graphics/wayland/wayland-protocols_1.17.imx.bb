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

ARCHIVE_NAME = "${BPN}-1.17"
SRC_URI = "https://wayland.freedesktop.org/releases/${ARCHIVE_NAME}.tar.xz \
           file://0001-unstable-Add-alpha-compositing-protocol.patch \
           file://0002-unstable-Add-hdr10-metadata-protocol.patch \
           file://0001-linux-dmabuf-support-passing-buffer-DTRC-meta-to-com.patch \
           "
SRC_URI[md5sum] = "55ddd5fdb02b73b9de9559aaec267315"
SRC_URI[sha256sum] = "df1319cf9705643aea9fd16f9056f4e5b2471bd10c0cc3713d4a4cdc23d6812f"
S = "${WORKDIR}/${ARCHIVE_NAME}"

UPSTREAM_CHECK_URI = "https://wayland.freedesktop.org/releases.html"

inherit autotools pkgconfig

PACKAGES = "${PN}"
FILES_${PN} += "${datadir}/pkgconfig/wayland-protocols.pc"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(imxfbdev|imxgpu)"
