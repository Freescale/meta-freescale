DESCRIPTION = "Merge prebuilt/extra files into rootfs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit allarch

SRC_URI = "file://merge"
S = "${WORKDIR}"

MERGED_DST ?= "${ROOT_HOME}"
do_install () {
    install -d ${D}/${MERGED_DST}
    find ${WORKDIR}/merge/ -maxdepth 1 -mindepth 1 -not -name README \
    -exec cp -fr '{}' ${D}/${MERGED_DST}/ \;
    find ${WORKDIR}/merge/ -maxdepth 1 -mindepth 1 -exec rm -fr '{}' \;
}
do_configure[noexec] = "1"
do_compile[noexec] = "1"

FILES:${PN} = "/*"
ALLOW_EMPTY:${PN} = "1"
INSANE_SKIP:${PN} = "debug-files dev-so"
