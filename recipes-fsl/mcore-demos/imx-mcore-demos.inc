# Copyright 2017-2021 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "i.MX M4/M7/M33 core Demo images"
SECTION = "app"
LICENSE = "Proprietary"

inherit deploy fsl-eula-unpack

SOC                ?= "INVALID"
SOC:mx7ulp-nxp-bsp  = "imx7ulp"
SOC:mx8dxl-nxp-bsp  = "imx8dxl"
SOC:mx8mm-nxp-bsp   = "imx8mm"
SOC:mx8mn-nxp-bsp   = "imx8mn"
SOC:mx8mnul-nxp-bsp = "imx8mnddr3l"
SOC:mx8mp-nxp-bsp   = "imx8mp"
SOC:mx8mpul-nxp-bsp = "imx8mp"
SOC:mx8mq-nxp-bsp   = "imx8mq"
SOC:mx8qm-nxp-bsp   = "imx8qm"
SOC:mx8qxp-nxp-bsp  = "imx8qx"
SOC:mx8dx-nxp-bsp   = "imx8qx"
SOC:mx8ulp-nxp-bsp  = "imx8ulp"
SOC:mx93-nxp-bsp    = "imx93"

MCORE_TYPE  ?= "m4"
MCORE_TYPE:mx8mn-nxp-bsp  = "m7"
MCORE_TYPE:mx8mnul-nxp-bsp = "m7"
MCORE_TYPE:mx8mp-nxp-bsp  = "m7"
MCORE_TYPE:mx8mpul-nxp-bsp  = "m7"
MCORE_TYPE:mx8ulp-nxp-bsp  = "m33"
MCORE_TYPE:mx93-nxp-bsp  = "m33"

SRC_URI = "${FSL_MIRROR}/${SOC}-${MCORE_TYPE}-demo-${PV}.bin;name=${SOC};fsl-eula=true"

S = "${WORKDIR}/${SOC}-${MCORE_TYPE}-demo-${PV}"

#SRC_URI="https://www.nxp.com/lgfiles/NMG/MAD/YOCTO//imx93-m33-demo-2.14.0.bin;fsl-eula=true;name=imx93"


SCR = "SCR-${SOC}-${MCORE_TYPE}-demo.txt"

do_install () {
    # install elf format binary to /lib/firmware
    install -d ${D}${base_libdir}/firmware
    install -m 0644 ${S}/*.elf ${D}${base_libdir}/firmware
}

DEPLOY_FILE_EXT       ?= "bin"
DEPLOY_FILE_EXT:mx7ulp-nxp-bsp = "img"

do_deploy () {
   # Install the demo binaries
   install -m 0644 ${S}/*.${DEPLOY_FILE_EXT} ${DEPLOYDIR}/
}

addtask deploy after do_install

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

FILES:${PN} = "${nonarch_base_libdir}/firmware"

INSANE_SKIP:${PN} = "arch"
