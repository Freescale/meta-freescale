SUMMARY = "Dummy package for SoCs lacking imx-test package"
DESCRIPTION = "Empty placeholder package that provides imx-test on SoCs where \
               the real test suite is unavailable, so images can depend on it \
               unconditionally."
HOMEPAGE = "https://github.com/nxp-imx/imx-test"
SECTION = "base"
LICENSE = "MIT"
# The license text is not shipped in the source tree, so reference the
# common-licenses copy; this is intentionally a local (non-remote) file.
# nooelint: oelint.var.licenseremotefile
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# Empty placeholder (ALLOW_EMPTY) with no upstream sources.
SRC_URI = ""

ALLOW_EMPTY:${PN} = "1"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(imx-generic-bsp)"
