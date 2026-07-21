SUMMARY = "The firmware of Cortex(R)-M33 for Arm(R) Ethos(TM)-U NPU"
DESCRIPTION = "Cortex(R)-M33 firmware image that drives the Arm(R) Ethos(TM)-U \
               NPU on i.MX93, installed into the kernel firmware directory for \
               loading at runtime."
HOMEPAGE = "https://github.com/nxp-imx/ethos-u-firmware"
SECTION = "firmware"
LICENSE = "Apache-2.0 AND BSD-3-Clause AND GPL-2.0-only"
LIC_FILES_CHKSUM = "\
    file://LICENSE.txt;md5=e3fc50a88d0a364313df4b21ef20c29e \
    file://LICENSE-GPL-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://LICENSE-BSD-3.txt;md5=0858ec9c7a80c4a2cf16e4f825a2cc91 \
"

SRC_URI = "${ETHOS_U_FIRMWARE_SRC};branch=${SRCBRANCH}"
ETHOS_U_FIRMWARE_SRC ?= "git://github.com/nxp-imx/ethos-u-firmware.git;protocol=https"
SRCBRANCH = "lf-6.18.2_1.0.0"
SRCREV = "5c14790b7ed63a52afd2ba6d917070e4c7b4265e"

ETHOS_U_FIRMWARE = "ethosu_firmware"
ETHOS_U_FIRMWARE:imx93-9x9-lpddr4-qsb = "ethosu_firmware_9x9"
ETHOS_U_FIRMWARE:imx93-11x11-lpddr4x-evk = "ethosu_firmware_11x11"
ETHOS_U_FIRMWARE:imx93-14x14-lpddr4x-evk = "ethosu_firmware_14x14"

do_install () {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ${S}/${ETHOS_U_FIRMWARE} ${D}${nonarch_base_libdir}/firmware/ethosu_firmware
}

# The package ships only the firmware blob installed above, so FILES is set
# explicitly to the firmware directory.
# nooelint: oelint.var.filesoverride
FILES:${PN} = "${nonarch_base_libdir}/firmware/*"
# The blob is a Cortex-M33 image, so its architecture never matches the target.
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN} = "arch"

COMPATIBLE_MACHINE = "(mx93-nxp-bsp)"
