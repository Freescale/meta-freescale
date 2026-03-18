SUMMARY = "NXP Wi-Fi driver for module 88w8801/8987/8997/9098 IW416/610/612"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ab04ac0f249af12befccb94447c08b77"

PROVIDES += "kernel-module-nxp89xx"
RREPLACES:${PN} = "kernel-module-nxp89xx"
RPROVIDES:${PN} = "kernel-module-nxp89xx"
RCONFLICTS:${PN} = "kernel-module-nxp89xx"

KERNEL_MODULE_PROBECONF += "moal"
module_conf_moal = "options moal mod_para=nxp/wifi_mod_para.conf"

SRC_URI = " \
    ${MRVL_SRC};branch=${SRCBRANCH} \
"
MRVL_SRC ?= "git://github.com/nxp-imx/mwifiex.git;protocol=https"
SRCBRANCH = "lf-6.18.2_1.0.0"
SRCREV = "a5fe4e194bf99315e349d81d77d6dfacec70757a"

inherit module

EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_BUILDDIR} -C ${STAGING_KERNEL_BUILDDIR} M=${S}"

KERNEL_MODULE_AUTOLOAD += "moal"
