DESCRIPTION = "Vector Packet Processing"

S = "${WORKDIR}/git"
PV = "18.01"

LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://LICENSE;md5=175792518e4ac015ab6696d16c4f607e"

AUTOTOOLS_SCRIPT_PATH = "${S}/src"

SRCREV = "d3111f07682585079069b91666afffbc73c79cd8"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/vpp;nobranch=1 \
	file://0001-Link-vpp-api-with-shared-libs-if-static-is-disabled.patch \
	file://0001-GCC-above-5.4-fails-when-we-specify-arch-funattribut.patch \
        file://config.h \
	"
DEPENDS = "dpdk openssl numactl bison-native vpp-core-native"
DEPENDS_class-native = "bison-native openssl-native"

inherit autotools
inherit pkgconfig
inherit python-dir

export ARCH="arm64"

EXTRA_OECONF = " \
	--disable-dependency-tracking \
	--with-libtool-sysroot=${SYSROOT} \
	--srcdir=${S}/src \
	--enable-perftool \
	--disable-papi \
	--disable-japi \
	--disable-static \
        --with-pre-data=128 \
        --disable-flowprobe-plugin --disable-ixge-plugin \
        --disable-memif-plugin --disable-sixrd-plugin --disable-gtpu-plugin \
        --disable-ioam-plugin --disable-lb-plugin --disable-ila-plugin \
        --disable-nat-plugin --disable-l2e-plugin --disable-stn-plugin \
        --disable-pppoe-plugin --disable-kubeproxy-plugin \
        --disable-vom   --disable-dpdk-plugin \
"

EXTRA_OECONF_append_class-native = " --disable-vlib"
CFLAGS += "-DCLIB_LOG2_CACHE_LINE_BYTES=6"

include vpp-pkgs.inc


do_configure_append () {
	( cd ${B} &&  mkdir -p vppinfra vpp/app )
          cp ${WORKDIR}/config.h ${S}/src/vlib/config.h 
}

do_install_append() {
	mkdir -p ${D}/etc/vpp
	cp ${S}/src/vpp/conf/startup.conf ${D}/etc/vpp/startup.conf
}


BBCLASSEXTEND = "native nativesdk"

COMPATIBLE_MACHINE_class-target = "(qoriq)"

