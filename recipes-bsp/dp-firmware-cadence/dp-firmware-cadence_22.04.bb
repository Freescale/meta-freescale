SUMMARY = "DP firmware"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=d3c315c6eaa43e07d8c130dc3a04a011"

inherit deploy fsl-eula-unpack

SRC_URI = "${FSL_MIRROR}/firmware-imx-8.16.bin;fsl-eula=true"

SRC_URI[md5sum] = "9ed2923c0eb511c7fcf37dd607944124"
SRC_URI[sha256sum] = "65f829a9e2597bffc58a680aaefa638122144a083633d1ae09b3aec1d9f8ab84"

S = "${WORKDIR}/firmware-imx-8.16"

do_install () {
    install -d ${D}/boot
    install -m 0644 ${S}/firmware/hdmi/cadence/dp_ls1028a.bin ${D}/boot/ls1028a-dp-fw.bin
}

do_deploy () {
    install -d ${DEPLOYDIR}/dp
    install -m 0644 ${S}/firmware/hdmi/cadence/dp_ls1028a.bin ${DEPLOYDIR}/dp/ls1028a-dp-fw.bin
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES:${PN}-image += "/boot"

COMPATIBLE_MACHINE = "(qoriq-arm64)"
PACKAGE_ARCH = "${MACHINE_ARCH}"
