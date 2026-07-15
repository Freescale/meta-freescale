SUMMARY = "Configure TSN funtionalitie"
DESCRIPTION = "A tool to configure TSN funtionalities in user space"
HOMEPAGE = "https://github.com/nxp-qoriq/tsntool"
SECTION = "console/network"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ef58f855337069acd375717db0dbbb6d"

DEPENDS = "cjson libnl readline"

inherit pkgconfig

SRC_URI = "git://github.com/nxp-qoriq/tsntool;protocol=https;nobranch=1 \
           file://0001-tsntool-remove-redundant-parameters-from-BIN_LDFLAGS.patch \
"
SRCREV = "a0769e23304957a22f0cbeee6d1f547b20c3c21e"

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
# libtsn.so is an unversioned runtime .so deliberately kept in the main
# package (hence the dev-so skip), so FILES:${PN} is set explicitly.
# nooelint: oelint.var.filesoverride
FILES:${PN} = "${libdir}/libtsn.so ${bindir}/*"
# Prebuilt-style .so with vendored rpaths and runtime deps QA can't map.
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN} += "file-rdeps rpaths dev-so"
COMPATIBLE_MACHINE = "(qoriq)"
PARALLEL_MAKE = ""
