SUMMARY = "NXP i.MX Secure Enclave Userspace Library"
DESCRIPTION = "NXP i.MX Secure Enclave Userspace Library"
SECTION = "base"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8636bd68fc00cc6a3809b7b58b45f982"

DEPENDS = "openssl"

inherit systemd

SRC_URI = "git://github.com/NXP/imx-secure-enclave.git;protocol=https;branch=lf-6.6.3_1.0.0"
SRCREV = "964affa2cb3f9f7fc85a6a18db60f9213f744495"

PV = "lf-6.6.3_1.0.0"

S = "${WORKDIR}/git"

EXTRA_OEMAKE += "PLAT=ele"

TARGET_CC_ARCH += "${LDFLAGS}"

do_install () {
    oe_runmake DESTDIR=${D} install
    rm -rf ${D}/usr/share
}

SYSTEMD_AUTO_ENABLE = "disable"
SYSTEMD_SERVICE:${PN} = "nvm_daemon.service"

COMPATIBLE_MACHINE = "(mx8ulp-nxp-bsp|mx9-nxp-bsp)"
