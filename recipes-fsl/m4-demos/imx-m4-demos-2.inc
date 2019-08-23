# Copyright 2017-2019 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "i.MX M4 core Demo images"
SECTION = "app"
LICENSE = "Proprietary"

inherit deploy fsl-eula-unpack

SOC        ?= "INVALID"
SOC_mx7ulp  = "imx7ulp"
SOC_mx8mm   = "imx8mm"
SOC_mx8mq   = "imx8mq"
SOC_mx8qm   = "imx8qm"
SOC_mx8qxp  = "imx8qx"

IMX_PACKAGE_NAME = "${SOC}-m4-demo-${PV}"
SRC_URI = "${FSL_MIRROR}${IMX_PACKAGE_NAME}.bin;fsl-eula=true"
SRC_URI_append = ";name=${SOC}"

S = "${WORKDIR}/${IMX_PACKAGE_NAME}"

do_install () {
    install -d ${D}
    cp -r ${S}/* ${D}
    if [ -d "${D}/usr/lib" ] && [ "${D}/usr/lib" != "${D}${libdir}" ]; then
        mv ${D}/usr/lib ${D}${libdir}
    fi
}

do_deploy () {
   # Install the demo binaries
   cp ${D}/* ${DEPLOYDIR}/
}

addtask deploy after do_install

FILES_${PN} = "/"
