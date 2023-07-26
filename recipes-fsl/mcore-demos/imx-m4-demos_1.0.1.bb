SUMMARY = "i.MX M4 core Demo images"
SECTION = "app"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM:mx7d-nxp-bsp = "file://COPYING;md5=8cf95184c220e247b9917e7244124c5a"

inherit deploy fsl-eula-unpack

M4_SOC ?= "INVALID"
M4_SOC:mx7d-nxp-bsp = "imx7d-sabresd"

SRC_URI = "${FSL_MIRROR}/${M4_SOC}-m4-freertos-${PV}.bin;fsl-eula=true"
S = "${WORKDIR}/${M4_SOC}-m4-freertos-${PV}"

SRC_URI[md5sum] = "b05b780ff3916f4953ab58ac95233c38"
SRC_URI[sha256sum] = "cc00d3b936d49b2794a2a99e10129437e70caba3fd26b8379b8c50dd22f73254"

do_deploy () {
   # Install the demo binaries
   install -d ${DEPLOYDIR}
   cp ${S}/*.bin ${DEPLOYDIR}/
   ls ${DEPLOYDIR}/
}

addtask deploy before do_build after do_compile

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(mx7d-nxp-bsp)"

