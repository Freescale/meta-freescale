# Copyright (C) 2019 NXP

SUMMARY = "OPTEE Client libs"
HOMEPAGE = "http://www.optee.org/"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=69663ab153298557a59c67a60a743e5b"

inherit systemd

SRCBRANCH = "master"
OPTEE_CLIENT_SRC ?= "git://github.com/OP-TEE/optee_client.git;protocol=https"
SRC_URI = "${OPTEE_CLIENT_SRC};branch=${SRCBRANCH}"
SRCREV = "3.7.0"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " file://tee-supplicant.service"

S = "${WORKDIR}/git"
SYSTEMD_SERVICE_${PN} = "tee-supplicant.service"

do_compile () {
    if [ ${TUNE_ARCH} = "aarch64" ]; then
        oe_runmake -C ${S} ARCH=arm64 O=${B}
    else
        oe_runmake -C ${S} ARCH=arm O=${B}
    fi
}

do_install () {
	oe_runmake -C ${S} install

	install -D -p -m0644 ${B}/export/usr/lib/libteec.so.1.0 ${D}${libdir}/libteec.so.1.0
	ln -sf libteec.so.1.0 ${D}${libdir}/libteec.so
	ln -sf libteec.so.1.0 ${D}${libdir}/libteec.so.1

	install -D -p -m0755 ${B}/export/usr/sbin/tee-supplicant ${D}${bindir}/tee-supplicant

	cp -a ${B}/export/usr/include ${D}/usr/

	sed -i -e s:/etc:${sysconfdir}:g -e s:/usr/bin:${bindir}:g ${WORKDIR}/tee-supplicant.service
	install -D -p -m0644 ${WORKDIR}/tee-supplicant.service ${D}${systemd_system_unitdir}/tee-supplicant.service
}

PACKAGES += "tee-supplicant"
FILES_${PN} += "${libdir}/* ${includedir}/*"
FILES_tee-supplicant += "${bindir}/tee-supplicant"

INSANE_SKIP_${PN} = "ldflags dev-elf"
INSANE_SKIP_${PN}-dev = "ldflags dev-elf"
INSANE_SKIP_tee-supplicant = "ldflags"

COMPATIBLE_MACHINE = "(imx)"
