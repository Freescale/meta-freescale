SUMMARY = "Merge extra files into the rootfs"
DESCRIPTION = "Merge prebuilt/extra files into rootfs"
HOMEPAGE = "https://github.com/Freescale/meta-freescale/"
SECTION = "base"
LICENSE = "MIT"
# The recipe ships no source tree of its own, so reference the shared
# common-licenses MIT copy.
# nooelint: oelint.var.licenseremotefile
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit allarch

SRC_URI = "file://merge"

MERGED_DST ?= "${ROOT_HOME}"
do_install () {
    install -d ${D}/${MERGED_DST}
    find ${UNPACKDIR}/merge/ -maxdepth 1 -mindepth 1 -not -name README \
    -exec cp -r '{}' ${D}/${MERGED_DST}/ \;
    find ${UNPACKDIR}/merge/ -maxdepth 1 -mindepth 1 -exec rm -fr '{}' \;
}
do_configure[noexec] = "1"
do_compile[noexec] = "1"

# This package deliberately merges arbitrary prebuilt files anywhere under
# the rootfs, so it claims everything (filesoverride) and skips the .so QA
# for any shared objects that come along.
# nooelint: oelint.var.filesoverride
FILES:${PN} = "/*"
ALLOW_EMPTY:${PN} = "1"
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN} = "dev-so"
