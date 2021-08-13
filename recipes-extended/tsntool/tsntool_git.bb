SUMMARY = "Configure TSN funtionalitie"
DESCRIPTION = "A tool to configure TSN funtionalities in user space"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ef58f855337069acd375717db0dbbb6d"

DEPENDS = "cjson libnl readline"

inherit pkgconfig

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/tsntool;protocol=https;nobranch=1"
SRCREV = "3111f7f79e7d1b1a5e60f37fe76785559b2d0360"

S = "${WORKDIR}/git"

do_configure[depends] += "virtual/kernel:do_shared_workdir"

do_compile:prepend() {
        mkdir -p ${S}/include/linux
        cp -r ${STAGING_KERNEL_DIR}/include/uapi/linux/tsn.h ${S}/include/linux
}     
do_install() {
    install -d ${D}${bindir} ${D}${libdir}
    install -m 0755 ${S}/tsntool ${D}${bindir}
    install -m 0755 ${S}/libtsn.so ${D}${libdir}
}

PACKAGES = "${PN}-dbg ${PN}"
FILES:${PN} = "${libdir}/libtsn.so ${bindir}/*"
INSANE_SKIP:${PN} += "file-rdeps rpaths dev-so"
COMPATIBLE_MACHINE = "(qoriq)"
PARALLEL_MAKE = ""
