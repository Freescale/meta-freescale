SUMMARY = "Debug agent for Freescale CodeWarrior"
SECTION = "apptrk"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://COPYING;md5=95560debfde180684364319811cc1421"

DEPENDS = "elfutils"

inherit kernel-arch

SRC_URI = "git://git.freescale.com/ppc/sdk/apptrk.git;branch=sdk-v1.9.x \
    file://apptrk-install-create-all-components-of-DEST.patch \
    file://no-strip.patch \
"
SRCREV = "4216af328452ed25a557759715a2087986a5a4bd"

S = "${WORKDIR}/git"

EXTRA_OEMAKE_qoriq-arm = "ARCH=arm"

CFLAGS_append = " -I${STAGING_INCDIR} -ISource/Linux -ISource/Portable"
CFLAGS_append_qoriq-arm = "-ISource/Linux_ARM -ISource/ARM"
CFLAGS_append_qoriq-ppc += "-ISource/Linux_PA -ISource/PA -DPPC"
CFLAGS_append_powerpc64 += "-DENABLE_64BIT_SUPPORT"

do_install() {
        oe_runmake install DESTDIR=${D}
}

COMPATIBLE_MACHINE = "(qoriq)"
