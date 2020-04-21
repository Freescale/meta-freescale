SUMMARY = "utility for security boot"
SECTION = "cst"
LICENSE = "BSD"

LIC_FILES_CHKSUM = "file://COPYING;md5=e959d5d617e33779d0e90ce1d9043eff"

DEPENDS += "openssl cst-native"
RDEPENDS_${PN} = "bash"

GENKEYS ?= "${STAGING_BINDIR_NATIVE}/cst/gen_keys"
GENKEYS_class-native = "./gen_keys"

inherit kernel-arch

# specify the non default keys pair for secure boot if needed
#SECURE_PRI_KEY = "/path/srk.pri"
#SECURE_PUB_KEY = "/path/srk.pub"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/cst;nobranch=1 \
"
SRCREV = "9aafbdf2441858ceca2779279b6876c6bdc2d3ed"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CC="${CC}" LD="${CC}"'

PARALLEL_MAKE = ""

do_install () {
    oe_runmake install DESTDIR=${D} BIN_DEST_DIR=${bindir}

    if [ -n "${SECURE_PRI_KEY}" ]; then
       cp -f ${SECURE_PRI_KEY} ${D}/${bindir}/cst/srk.pri
       cp -f ${SECURE_PUB_KEY} ${D}/${bindir}/cst/srk.pub
    elif [ ! -f ${D}/${bindir}/cst/srk.pri -o ! ${D}/${bindir}/cst/srk.pub ]; then
       cd ${D}/${bindir}/cst  && ${GENKEYS} 1024
    fi
}

FILES_${PN}-dbg += "${bindir}/cst/.debug"
BBCLASSEXTEND = "native nativesdk"
