SUMMARY = "Test programs for IMX BSP"
DESCRIPTION = "Linux HDCP test application for imx6 platform"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://EULA.txt;md5=93b784b1c11b3fffb1638498a8dde3f6"

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"

SRC_URI[md5sum] = "7289f732beac67c50a1857d86e43c6eb"
SRC_URI[sha256sum] = "a64075953103da6a06714bdacb3bfa43845c2bd304d1cd00b5443d3edf02d904"

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

inherit fsl-eula-unpack

do_install() {
    install -d ${D}/unit_tests
    install -m 755 ${S}/*.out ${D}/unit_tests/
}

FILES_${PN} += "/unit_tests"

COMPATIBLE_MACHINE = "(mx6)"
