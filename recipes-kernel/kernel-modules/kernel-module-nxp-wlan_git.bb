SUMMARY = "NXP Wi-Fi driver for module 88w8801/8987/8997/9098 IW416/610/612"
DESCRIPTION = "Kernel driver for NXP 88W8801/8987/8997/9098 and IW416/610/612 Wi-Fi modules."
HOMEPAGE = "https://github.com/nxp-imx/mwifiex"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ab04ac0f249af12befccb94447c08b77"

PROVIDES += "kernel-module-nxp89xx"

SRC_URI = "\
    ${MRVL_SRC};branch=${SRCBRANCH} \
    file://0001-mlinux-moal_main-fix-device-wakeup-capability.patch \
    file://0003-mlinux-Take-the-cfg80211-cookie-as-input-on-Linux-7.3.patch \
    file://nxp-wlan-btnxpuart.conf \
"
MRVL_SRC ?= "git://github.com/nxp-imx/mwifiex.git;protocol=https"
SRCBRANCH = "lf-6.18.37_2.1.0"
SRCREV = "44f90628e368de23e9991de424fc2a1799c81044"

# module.bbclass sets FILES:${PN} = "" after this point, so append.
FILES:${PN}:append = " ${modprobedir}/nxp-wlan-btnxpuart.conf"
CONFFILES:${PN} += "${modprobedir}/nxp-wlan-btnxpuart.conf"

RREPLACES:${PN} = "kernel-module-nxp89xx"
RPROVIDES:${PN} = "kernel-module-nxp89xx"
RCONFLICTS:${PN} = "kernel-module-nxp89xx"

KERNEL_MODULE_PROBECONF += "moal"
module_conf_moal = "options moal mod_para=nxp/wifi_mod_para.conf"

inherit module

EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_BUILDDIR} -C ${STAGING_KERNEL_BUILDDIR} M=${S}"

KERNEL_MODULE_AUTOLOAD += "moal"

do_install:append() {
    # A bbappend can replace SRC_URI and drop this file (meta-imx does).
    if [ -e ${UNPACKDIR}/nxp-wlan-btnxpuart.conf ]; then
        install -D -m 0644 ${UNPACKDIR}/nxp-wlan-btnxpuart.conf ${D}${modprobedir}/nxp-wlan-btnxpuart.conf
    fi
}
