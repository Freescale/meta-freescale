# Copyright 2020-2021 NXP

SUMMARY = "OPTEE Client libs"
HOMEPAGE = "http://www.optee.org/"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=69663ab153298557a59c67a60a743e5b"

inherit python3native systemd

SRC_URI = "git://github.com/nxp-qoriq/optee_client.git;protocol=https;nobranch=1"
SRCREV = "7c9c423d00e96bf51debd5fe10fd70dce83be5cc"

FILESEXTRAPATHS:prepend := "${THISDIR}/optee-client:"
SRC_URI += "file://tee-supplicant.service"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

OPTEE_ARCH ?= "arm32"
OPTEE_ARCH:armv7a = "arm32"
OPTEE_ARCH:aarch64 = "arm64"

EXTRA_OEMAKE = "ARCH=${OPTEE_ARCH} O=${B}"

do_install () {
	oe_runmake -C ${S} install

	install -d ${D}${libdir}/
	install -p -m0644 ${B}/export${libdir}/libteec.so.1.0.0 ${D}${libdir}/
	ln -sf libteec.so.1.0.0 ${D}${libdir}/libteec.so.1.0
	ln -sf libteec.so.1.0.0 ${D}${libdir}/libteec.so.1
	ln -sf libteec.so.1 ${D}${libdir}/libteec.so

	install -D -p -m0644 ${B}/export/usr/lib/libckteec.so.0.1.0 ${D}${libdir}/libckteec.so.0.1.0
	ln -sf libckteec.so.0.1.0 ${D}${libdir}/libckteec.so.0.1
	ln -sf libckteec.so.0.1.0 ${D}${libdir}/libckteec.so.0
	ln -sf libckteec.so.0.1.0 ${D}${libdir}/libckteec.so

	install -D -p -m0755 ${B}/export/usr/sbin/tee-supplicant ${D}${bindir}/tee-supplicant

	cp -a ${B}/export/usr/include ${D}${includedir}

	install -d ${D}${systemd_system_unitdir}/
	install -m0644 ${WORKDIR}/tee-supplicant.service ${D}${systemd_system_unitdir}/
	sed -i -e s:/etc:${sysconfdir}:g -e s:/usr/bin:${bindir}:g ${D}${systemd_system_unitdir}/tee-supplicant.service
}

SYSTEMD_SERVICE:${PN} = "tee-supplicant.service"

FILES:${PN} += "${libdir}/* ${includedir}/*"

INSANE_SKIP:${PN} = "ldflags dev-elf"
INSANE_SKIP:${PN}-dev = "ldflags dev-elf"
