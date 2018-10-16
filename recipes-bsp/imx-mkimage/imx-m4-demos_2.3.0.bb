# Copyright 2017-2018 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "i.MX M4 core Demo images"
SECTION = "app"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=08fd295cce89b0a9c74b9b83ed74f671"

inherit deploy fsl-eula-unpack2

SOC ?= "imx8qm"
SOC_mx7ulp= "imx7ulp"
SOC_mx8mq= "imx8mq"
SOC_mx8qm= "imx8qm"
SOC_mx8qxp= "imx8qx"

IMX_PACKAGE_NAME = "${SOC}-m4-demo-${PV}"
SRC_URI_NAME = "${SOC}"

SRC_URI[imx7ulp.md5sum] = "601472aa2056c34324ba8b2d8b656d10"
SRC_URI[imx7ulp.sha256sum] = "6ad1d21356a5bbfdc52e4709348c7bee49e0731ef0a106f599c78817e14d3d12"

SRC_URI[imx8mq.md5sum] = "c2aeda4ca7cc7d1c2916be7dd42b946f"
SRC_URI[imx8mq.sha256sum] = "fd441e75395b0c6f90626c883ee8a93406b14e7d55adc7925116254394bb7ad8"

SRC_URI[imx8qm.md5sum] = "b75dda504083ac1ee05423974c22bbce"
SRC_URI[imx8qm.sha256sum] = "fdc486af0a02e3093559b31cb9c982f05c209bfc03da0910062091c406bfb496"

SRC_URI[imx8qx.md5sum] = "b18198150c3aa5b1e01aba2047166728"
SRC_URI[imx8qx.sha256sum] = "3b3887694b7cfe02d2918b0780f941da3d5c0e867725927b8c8f19fbff106bc9"

SCR = "SCR-${SOC}-m4-demo.txt"

do_deploy () {
   # Install the demo binaries
   cp ${D}/* ${DEPLOYDIR}/
}

addtask deploy before do_build after do_install

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx7ulp|mx8mq|mx8qm|mx8qxp)"
