SUMMARY = "Debug agent for Freescale CodeWarrior"
SECTION = "apptrk"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://COPYING;md5=95560debfde180684364319811cc1421"

DEPENDS = "elfutils"

inherit kernel-arch

SRC_URI = "git://git.freescale.com/ppc/sdk/apptrk.git;nobranch=1 \
    file://apptrk-install-create-all-components-of-DEST.patch \
    file://no-strip.patch \
"
SRCREV = "cbed10997c5e2a4aaa004fb0e1efec858bf1bbe1"

S = "${WORKDIR}/git"

EXTRA_OEMAKE_qoriq-arm = "ARCH=arm"

CFLAGS += "-I${STAGING_INCDIR} -ISource/Linux -ISource/Portable"
CFLAGS_qoriq-arm += "-ISource/Linux_ARM -ISource/ARM"
CFLAGS_qoriq-ppc += "-ISource/Linux_PA -ISource/PA -DPPC"
CFLAGS_powerpc64 += "-DENABLE_64BIT_SUPPORT"

do_install() {
        oe_runmake install DESTDIR=${D}
}

COMPATIBLE_MACHINE = "(qoriq)"
