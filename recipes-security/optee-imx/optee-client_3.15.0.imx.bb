# Copyright (C) 2017-2021 NXP

SUMMARY = "OPTEE Client libs"
HOMEPAGE = "http://www.optee.org/"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=69663ab153298557a59c67a60a743e5b"

SRCBRANCH = "lf-5.15.5_1.0.0"
SRC_URI = " \
    git://source.codeaurora.org/external/imx/imx-optee-client.git;protocol=https;branch=${SRCBRANCH} \
    file://tee-supplicant.service \
"

SRCREV = "182874320395787a389e5b0f7df02b32f3c0a1b0"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

inherit python3native systemd features_check

REQUIRED_MACHINE_FEATURES = "optee"

SYSTEMD_SERVICE:${PN} = "tee-supplicant.service"

OPTEE_ARCH ?= "arm32"
OPTEE_ARCH:armv7a = "arm32"
OPTEE_ARCH:aarch64 = "arm64"

EXTRA_OEMAKE = "ARCH=${OPTEE_ARCH} O=${B}"

do_install () {
	oe_runmake -C ${S} install

	install -D -p -m0644 ${B}/export/usr/lib/libteec.so.1.0.0 ${D}${libdir}/libteec.so.1.0.0
	ln -sf libteec.so.1.0.0 ${D}${libdir}/libteec.so.1
	ln -sf libteec.so.1.0.0 ${D}${libdir}/libteec.so

	install -D -p -m0644 ${B}/export/usr/lib/libckteec.so.0.1.0 ${D}${libdir}/libckteec.so.0.1.0
	ln -sf libckteec.so.0.1.0 ${D}${libdir}/libckteec.so.0
	ln -sf libckteec.so.0.1.0 ${D}${libdir}/libckteec.so

	install -D -p -m0755 ${B}/export/usr/sbin/tee-supplicant ${D}${bindir}/tee-supplicant

	cp -a ${B}/export/usr/include ${D}${includedir}

	sed -i -e s:/etc:${sysconfdir}:g -e s:/usr/bin:${bindir}:g ${WORKDIR}/tee-supplicant.service
	install -D -p -m0644 ${WORKDIR}/tee-supplicant.service ${D}${systemd_system_unitdir}/tee-supplicant.service
}

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
