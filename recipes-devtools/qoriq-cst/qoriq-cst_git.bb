SUMMARY = "utility for security boot"
DESCRIPTION = "Code Signing Tool (CST) for QorIQ secure boot: generates keys, signs images and produces the CSF headers consumed by the SoC boot ROM"
HOMEPAGE = "https://github.com/nxp-qoriq/cst"
SECTION = "cst"
LICENSE = "BSD-3-Clause"

LIC_FILES_CHKSUM = "file://LICENSE;md5=e959d5d617e33779d0e90ce1d9043eff"

DEPENDS += "openssl qoriq-cst-native"

GENKEYS ?= "${STAGING_BINDIR_NATIVE}/cst/gen_keys"
GENKEYS:class-native = "./gen_keys"

inherit kernel-arch

# specify the non default keys pair for secure boot if needed
#SECURE_PRI_KEY = "/path/srk.pri"
#SECURE_PUB_KEY = "/path/srk.pub"

SRC_URI = "git://github.com/nxp-qoriq/cst;protocol=https;nobranch=1"
SRCREV = "892d2ed3207d78a3cb5533eeb91bcc73967e3e36"

EXTRA_OEMAKE = 'CC="${CC}" LD="${CC}"'
CFLAGS:append = " -Wno-deprecated-declarations"

PARALLEL_MAKE = ""

do_install () {
    oe_runmake install DESTDIR=${D} BIN_DEST_DIR=${bindir}

    if [ -n "${SECURE_PRI_KEY}" ]; then
       install -m 0600 ${SECURE_PRI_KEY} ${D}/${bindir}/cst/srk.pri
       install -m 0644 ${SECURE_PUB_KEY} ${D}/${bindir}/cst/srk.pub
    elif [ ! -f ${D}/${bindir}/cst/srk.pri -o ! ${D}/${bindir}/cst/srk.pub ]; then
       cd ${D}/${bindir}/cst  && ${GENKEYS} 1024
    fi
}

FILES:${PN}-dbg += "${bindir}/cst/.debug"
RDEPENDS:${PN} = "bash"
BBCLASSEXTEND = "native nativesdk"
# The cst tools live under ${bindir}/cst and retain build-host paths.
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN}-dbg += "buildpaths"

