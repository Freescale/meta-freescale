SUMMARY = "utility for security boot"
SECTION = "cst"
LICENSE = "BSD-3-Clause"

LIC_FILES_CHKSUM = "file://COPYING;md5=e959d5d617e33779d0e90ce1d9043eff"

DEPENDS += "openssl qoriq-cst-native"
RDEPENDS:${PN} = "bash"

GENKEYS ?= "${STAGING_BINDIR_NATIVE}/cst/gen_keys"
GENKEYS:class-native = "./gen_keys"

inherit kernel-arch

# specify the non default keys pair for secure boot if needed
#SECURE_PRI_KEY = "/path/srk.pri"
#SECURE_PUB_KEY = "/path/srk.pub"

SRC_URI = "git://github.com/nxp-qoriq/cst;protocol=https;nobranch=1 \
           file://0001-tools-Mark-struct-input_field-file_field-extern.patch \
"
SRCREV = "af56e6c5c66dd2bc86a83b0bee8cb61b88d2120c"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CC="${CC}" LD="${CC}"'
CFLAGS:append = ' -Wno-deprecated-declarations'

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

FILES:${PN}-dbg += "${bindir}/cst/.debug"
BBCLASSEXTEND = "native nativesdk"
