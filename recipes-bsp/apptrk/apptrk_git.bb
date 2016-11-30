SUMMARY = "Debug agent for Freescale CodeWarrior"
SECTION = "apptrk"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://EULA;md5=c9ae442cf1f9dd6c13dfad64b0ffe73f"

DEPENDS = "elfutils"

inherit kernel-arch

SRC_URI = "git://git.freescale.com/ppc/sdk/apptrk.git;branch=sdk-v2.0.x \
    file://apptrk-install-create-all-components-of-DEST.patch \
    file://no-strip.patch \
"
SRCREV = "873f44ca6b219508f738825299453d92975fb897"

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
PACKAGE_ARCH = "${MACHINE_SOCARCH}"

