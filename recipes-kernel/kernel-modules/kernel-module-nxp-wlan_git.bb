SUMMARY = "NXP Wi-Fi driver for module 88w8801/8987/8997/9098 IW416/610/612"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ab04ac0f249af12befccb94447c08b77"

# For backwards compatibility
PROVIDES += "kernel-module-nxp89xx"
RREPLACES:${PN} = "kernel-module-nxp89xx"
RPROVIDES:${PN} = "kernel-module-nxp89xx"
RCONFLICTS:${PN} = "kernel-module-nxp89xx"

SRCBRANCH = "lf-6.12.20_2.0.0"
MRVL_SRC ?= "git://github.com/nxp-imx/mwifiex.git;protocol=https"
SRC_URI = " \
    ${MRVL_SRC};branch=${SRCBRANCH} \
    file://mlinux-moal_main-lower-PRINTM_MMSG-log-level-to-KERN_INFO.patch \
"
SRCREV = "7a8beaa1605cb0870dc7ba3312c76df91cb0d6cf"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_BUILDDIR} -C ${STAGING_KERNEL_BUILDDIR} M=${S}"
