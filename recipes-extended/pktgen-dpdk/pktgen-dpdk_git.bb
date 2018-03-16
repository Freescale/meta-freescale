DESCRIPTION = "PKTGEN DPDK"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8120b89a783571da3cb492c35ac6b1f9"

DEPENDS += "libpcap dpdk"

SRC_URI = "git://dpdk.org/git/apps/pktgen-dpdk;protocol=http;nobranch=1"
SRCREV = "ffbe7becf8dd75fd7d46b97b10374531878cb9c5"

S = "${WORKDIR}/git"

DPAA_VER ?= "dpaa"
export RTE_TARGET = "arm64-${DPAA_VER}-linuxapp-gcc"
export RTE_SDK = "${RECIPE_SYSROOT}/usr/share"

EXTRA_OEMAKE += 'CC="${CC}" LD="${LD}"  RTE_SDK="${RECIPE_SYSROOT}/usr/share" RTE_TARGET="arm64-${DPAA_VER}-linuxapp-gcc"'

do_compile() {
      unset LDFLAGS TARGET_LDFLAGS BUILD_LDFLAGS
      oe_runmake 
}

do_install() {
	install -d ${D}${bindir}/
	cp -f  ${S}/app/arm64-dpaa-linuxapp-gcc/pktgen ${D}${bindir}/
        cp -f  ${S}/Pktgen.lua ${D}${bindir}/
}

INSANE_SKIP_${PN} = "ldflags"
INHIBIT_PACKAGE_STRIP = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"
PARALLEL_MAKE = ""
