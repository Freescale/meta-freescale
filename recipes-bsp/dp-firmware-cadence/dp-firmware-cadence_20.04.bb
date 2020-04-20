SUMMARY = "DP firmware"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=228c72f2a91452b8a03c4cab30f30ef9"

inherit deploy fsl-eula-unpack 

SRC_URI = "https://www.nxp.com/lgfiles/sdk/lsdk2004/firmware-cadence-lsdk2004.bin;fsl-eula=true"

SRC_URI[md5sum] = "d7eb8ef87cae8e4a205c266b3ac6f330"
SRC_URI[sha256sum] = "bef89a3eceed95e90748a2301d6f6ffbdf1a72237c0045093cb9485b05eb8fbd"

S = "${WORKDIR}/firmware-cadence-lsdk2004"

do_install () {
    install -d ${D}/boot
    cp -fr ${S}/dp/*.bin ${D}/boot
}

do_deploy () {
    install -d ${DEPLOYDIR}/dp
    cp -fr ${S}/dp/*.bin ${DEPLOYDIR}/dp
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

COMPATIBLE_MACHINE = "(qoriq-arm64)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

