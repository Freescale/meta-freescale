DESCRIPTION = "Vector Packet Processing"

S = "${WORKDIR}/git/"
PV = "19.01"

LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://LICENSE;md5=175792518e4ac015ab6696d16c4f607e"

OECMAKE_SOURCEPATH = "${S}/src"

SRCREV = "ea4f900311027e4c50f101e08c12920f6e2ddf8d"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/vpp;nobranch=1 \
	file://0001-GCC-above-5.4-fails-when-we-specify-arch-funattribut.patch \
        file://0001-getcpu-rename-getcpu-to-avoid-conflict-with-glibc-2..patch \
        file://0001-vpp-core-fix-package_qa-error.patch \
"
DEPENDS = "dpdkvpp openssl python-ply util-linux vpp-core-native"

DEPENDS_class-native = "openssl-native  python-ply-native util-linux-native"

inherit cmake
inherit pkgconfig
inherit python-dir pythonnative

export ARCH="arm64"
export OPENSSL_PATH = "${RECIPE_SYSROOT}"
export DPDK_PATH= "${RECIPE_SYSROOT}" 

EXTRA_OECONF = " \
	--with-libtool-sysroot=${SYSROOT} \
	--srcdir=${S}/src \
        --with-pre-data=128 \
        --without-libnuma \
        --without-ipv6sr \
"

CFLAGS += " -mtls-dialect=trad -DCLIB_LOG2_CACHE_LINE_BYTES=6 -I${OPENSSL_PATH}/usr/include  -L${OPENSSL_PATH}/lib"

do_install_append() {
        mkdir -p ${D}/etc/vpp
        cp ${S}/src/vpp/conf/startup.conf ${D}/etc/vpp/startup.conf
}

include vpp-pkgs.inc

BBCLASSEXTEND = "native nativesdk"

COMPATIBLE_MACHINE_class-target = "(qoriq)"
