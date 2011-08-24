DESCRIPTION = "Sanitized set of 2.6 kernel headers for the C library's use."
SECTION = "libs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

# Upstream doesn't include imx headers
#SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v2.6/linux-${PV}.tar.bz2"
#SRC_URI[md5sum] = "38d0e15a2be7f5e58f46a959ec9ec4d3"
#SRC_URI[sha256sum] = "19983d5daee9053e49303041365be7c55bbaf08941aea07dc8362d44742c26f6"

PR = "r0"
INHIBIT_DEFAULT_DEPS = "1"
DEPENDS += "unifdef-native"
RDEPENDS_${PN}-dev = ""

REL="11.05.01"
SRC_URI = "http://opensource.freescale.com/pub/scm/imx/linux-${PV}.tar.bz2;name=source \
           http://opensource.freescale.com/pub/scm/imx/linux-${PV}-imx_${REL}.bz2;name=patchs"

SRC_URI[source.md5sum] = "38d0e15a2be7f5e58f46a959ec9ec4d3"
SRC_URI[source.sha256sum] = "19983d5daee9053e49303041365be7c55bbaf08941aea07dc8362d44742c26f6"
SRC_URI[patchs.md5sum] = "e6c24894017f3b5977ecfa07e28dc69e"
SRC_URI[patchs.sha256sum] = "20b6f1adb14613c1eda1d7e353e169753ad45c7544e2bc590bf3b9a998d61984"

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
