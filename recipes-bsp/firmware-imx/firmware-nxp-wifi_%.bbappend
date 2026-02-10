# Use the latest revision

SUMMARY = "NXP Wi-Fi Firmware"
DESCRIPTION = "Firmware files for NXP Wi-Fi/Bluetooth modules"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=bc649096ad3928ec06a8713b8d787eac"

# Use the latest revision
IMX_FIRMWARE_SRC ?= "git://github.com/nxp-imx/imx-firmware.git;protocol=https"
SRC_URI = "${IMX_FIRMWARE_SRC};branch=${SRCBRANCH}"
SRCBRANCH = "lf-6.12.49_2.2.0"
SRCREV = "8c9b278016c97527b285f2fcbe53c2d428eb171d"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/nxp
    oe_runmake install INSTALLDIR=${D}${nonarch_base_libdir}/firmware/nxp
}

# Remove a specific package if you don't want it in the final image
PACKAGES:remove = "${PN}-nxp8801-sdio"

# Adjust dependencies for the 'all' meta-package
RDEPENDS:${PN}-all-sdio:remove = "${PN}-nxp8801-sdio"
RDEPENDS:${PN}-all-sdio += "${PN}-nxp8997-sdio"

# Ensure the USB package doesn't fail the build if it contains no files
ALLOW_EMPTY:${PN}-all-usb = "1"
