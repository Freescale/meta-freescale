SUMMARY = "Auto Response Control Module"
LICENSE = "GPL-2.0-only & BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=b5881ecf398da8a03a3f4c501e29d287"

inherit module

SRC_URI = "git://github.com/nxp-qoriq-yocto-sdk/auto-resp;branch=nxp/sdk-v2.0.x"
SRCREV =  "9a74743167dcfcfbca5056eedbff9a52337c9712"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX} SYSROOT=${STAGING_DIR_TARGET}"
export KERNEL_PATH

INHIBIT_PACKAGE_STRIP = "1"

do_compile:prepend() {
    sed -i -e 's,EXTRA_CFLAGS += -I$(PWD),EXTRA_CFLAGS += -I${S},' ${S}/armodule/source/Makefile
}

do_install(){
	install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}
	install -d ${D}${bindir}
	install -m 644 ${B}/bin/ar.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/
	cp -f ${S}/bin/ar_* ${D}${bindir}/ 
}

FILES:${PN} += "${bindir}/"
INSANE_SKIP:${PN} = "ldflags"
COMPATIBLE_MACHINE = "(t1040|t1042)"

CLEANBROKEN = "1"
