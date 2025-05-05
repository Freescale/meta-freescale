SUMMARY = "NXP Wi-Fi driver for module 88w8801/8987/8997/9098 IW416/610/612"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ab04ac0f249af12befccb94447c08b77"

# For backwards compatibility
PROVIDES += "kernel-module-nxp89xx"
RREPLACES:${PN} = "kernel-module-nxp89xx"
RPROVIDES:${PN} = "kernel-module-nxp89xx"
RCONFLICTS:${PN} = "kernel-module-nxp89xx"

SRCBRANCH = "lf-6.6.52_2.2.0"
MRVL_SRC ?= "git://github.com/nxp-imx/mwifiex.git;protocol=https"
SRC_URI = " \
    ${MRVL_SRC};branch=${SRCBRANCH} \
    file://wlan_src_driver_patch_release_lf-6.6.52-2.2.0.patch \
    file://mlinux-moal_main-lower-PRINTM_MMSG-log-level-to-KERN_INFO.patch \
"
SRCREV = "5ad19e194f49ed9447bee7864eb562618ccaf9b1"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_BUILDDIR} -C ${STAGING_KERNEL_BUILDDIR} M=${S}"
