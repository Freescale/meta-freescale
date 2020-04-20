DESCRIPTION = "PKTGEN DPDK"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=30ac8fa65a07ea7cc1c7ee84e1c80294"

DEPENDS += "libpcap dpdk lua lua-native"

SRC_URI = "git://dpdk.org/git/apps/pktgen-dpdk;protocol=https;nobranch=1 \
"
SRCREV = "7a61e4dfcdf037c31460b6c9bcb301797813036a"

S = "${WORKDIR}/git"

DPAA_VER ?= "dpaa"
export RTE_TARGET = "arm64-${DPAA_VER}-linuxapp-gcc"
export RTE_SDK = "${RECIPE_SYSROOT}/usr/share/"

EXTRA_OEMAKE += 'CC="${CC}" LD="${LD}"  RTE_SDK="${RECIPE_SYSROOT}/usr/share/" RTE_TARGET="arm64-${DPAA_VER}-linuxapp-gcc"'

do_compile() {
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
COMPATIBLE_MACHINE = "(qoriq-arm64)"
