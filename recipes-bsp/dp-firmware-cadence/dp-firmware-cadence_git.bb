SUMMARY = "DP firmware"
LICENSE = "NXP-Binary-EULA"
LIC_FILES_CHKSUM = "file://COPYING;md5=6c12031a11b81db21cdfe0be88cac4b3"

inherit deploy fsl-eula-unpack 

SRC_URI = "http://www.nxp.com/lgfiles/sdk/lsdk1909/firmware-cadence-lsdk1909.bin;fsl-eula=true"

SRC_URI[md5sum] = "07cab3eea88dbf3150b55a91624f0715"
SRC_URI[sha256sum] = "5871c5717be32f14a59624d5699d405e8ce9ae2e9c9ef86fd16e13d264a40e58"

S = "${WORKDIR}/firmware-cadence-lsdk1909"

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

