DESCRIPTION = "Sanitized set of 2.6 kernel headers for the C library's use."
SECTION = "libs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

PR = "r1"
INHIBIT_DEFAULT_DEPS = "1"
DEPENDS += "unifdef-native"
RDEPENDS_${PN}-dev = ""

REL="11.09.01"
SRC_URI = "http://www.kernel.org/pub/linux/kernel/v2.6/linux-${PV}.tar.bz2;name=source \
           file://linux-${PV}-imx_${REL}.bz2;name=patchs"

SRC_URI[source.md5sum] = "5ebff49fd65a7bad234ce103b0b3ede0"
SRC_URI[source.sha256sum] = "83149a9ab30f8dfe45c63d735018422909cf0dd3f5b5501e42f7349f4044f5f1"
SRC_URI[patchs.md5sum] = "02b43a4ed6e4d0adc6841ee07fee02f1"
SRC_URI[patchs.sha256sum] = "7d3adf8f83e49b57f3de74e6684bc6ffc823de4f17b817535bb9a94a6a44ce5b"

do_patch () {
    # Apply distributed kernel patchs
    cd ${S}
    tar xf ${WORKDIR}/linux-${PV}-imx_${REL}
    ./patches/patch-kernel.sh
}

S = "${WORKDIR}/linux-${PV}"

set_arch() {
	case ${TARGET_ARCH} in
		arm*)     ARCH=arm ;;
	esac
}

do_configure() {
	set_arch
	oe_runmake allnoconfig ARCH=$ARCH
}

do_compile () {
}

do_install() {
	set_arch
	oe_runmake headers_install INSTALL_HDR_PATH=${D}${exec_prefix} ARCH=$ARCH
	# Kernel should not be exporting this header
	rm -f ${D}${exec_prefix}/include/scsi/scsi.h
    # The ..install.cmd conflicts between various configure runs
    find ${D}${includedir} -name ..install.cmd | xargs rm -f
}

BBCLASSEXTEND = "nativesdk"
