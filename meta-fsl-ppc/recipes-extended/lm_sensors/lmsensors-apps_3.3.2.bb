SUMMARY = "A hardware health monitoring package for Linux"
DESCRIPTION = "Lm-sensors is a hardware health monitoring package for Linux. \
               It allows you to access information from temperature, voltage, \
               and fan speed sensors."
HOMEPAGE = "http://www.lm-sensors.org/"
DEPENDS = "sysfsutils virtual/libiconv \
           bison-native flex-native"
LICENSE = "GPLv2 & LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe \
                    file://COPYING.LGPL;md5=4fbd65380cdd255951079008b364516c"

PR = "r2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "http://dl.lm-sensors.org/lm-sensors/releases/lm_sensors-${PV}.tar.bz2"

SRC_URI[md5sum] = "f357ba00b080ab102a170f7bf8bb2578"
SRC_URI[sha256sum] = "f13dd885406841a7352ccfb8b9ccb23c4c057abe3de4258da5444c149a9e3ae1"

S = "${WORKDIR}/lm_sensors-${PV}"

EXTRA_OEMAKE = 'LINUX=${STAGING_KERNEL_DIR} EXLDFLAGS="${LDFLAGS}" \
		MACHINE=${TARGET_ARCH} PREFIX=${prefix} CC="${CC}" \
		AR="${AR}" MANDIR=${mandir}'

do_compile() {
	oe_runmake user PROG_EXTRA=sensors
}

do_install() {
	oe_runmake user_install DESTDIR=${D}
}

PACKAGES =+ "libsensors libsensors-dev libsensors-staticdev libsensors-dbg libsensors-doc"
PACKAGES =+ "lmsensors-sensors lmsensors-sensors-dbg lmsensors-sensors-doc"
PACKAGES =+ "lmsensors-scripts"

FILES_lmsensors-scripts = "${bindir}/*.pl ${bindir}/ddcmon ${sbindir}/fancontrol* ${sbindir}/pwmconfig ${sbindir}/sensors-detect"
RDEPENDS_lmsensors-scripts += "lmsensors-sensors perl bash"
RDEPENDS_lmsensors-apps += "perl-module-strict perl-module-vars perl-module-warnings-register perl-module-warnings"
RDEPENDS_lmsensors-scripts += "perl-module-fcntl perl-module-exporter perl-module-xsloader perl-module-exporter-heavy perl-module-file-basename perl-module-constant"

FILES_lmsensors-sensors = "${bindir}/sensors ${sysconfdir}"
FILES_lmsensors-sensors-dbg += "${bindir}/.debug/sensors"
FILES_lmsensors-sensors-doc = "${mandir}/man1 ${mandir}/man5"
FILES_libsensors = "${libdir}/libsensors.so.*"
FILES_libsensors-dbg += "${libdir}/.debug"
FILES_libsensors-dev = "${libdir}/libsensors.so ${includedir}"
FILES_libsensors-staticdev = "${libdir}/libsensors.a"
FILES_libsensors-doc = "${mandir}/man3"
