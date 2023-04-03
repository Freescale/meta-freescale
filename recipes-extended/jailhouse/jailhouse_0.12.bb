SUMMARY = "Jailhouse, i.MX fork"
HOMEPAGE = "https://github.com/siemens/jailhouse"
SECTION = "jailhouse"
LICENSE = "GPL-2.0"

LIC_FILES_CHKSUM = "file://COPYING;md5=9fa7f895f96bde2d47fd5b7d95b6ba4d \
                 file://tools/root-cell-config.c.tmpl;beginline=6;endline=33;md5=2825581c1666c44a17955dc574cfbfb3 \
                 file://include/jailhouse/hypercall.h;beginline=9;endline=36;md5=2825581c1666c44a17955dc574cfbfb3 \
                 file://include/jailhouse/cell-config.h;beginline=9;endline=36;md5=2825581c1666c44a17955dc574cfbfb3 \
                 file://include/arch/arm/asm/jailhouse_hypercall.h;beginline=9;endline=36;md5=2825581c1666c44a17955dc574cfbfb3 \
                 file://include/arch/arm64/asm/jailhouse_hypercall.h;beginline=9;endline=36;md5=2825581c1666c44a17955dc574cfbfb3 \
                 file://include/arch/x86/asm/jailhouse_hypercall.h;beginline=9;endline=36;md5=2825581c1666c44a17955dc574cfbfb3 \
                 file://driver/jailhouse.h;beginline=9;endline=36;md5=2825581c1666c44a17955dc574cfbfb3 \
"

SRCBRANCH = "imx_5.4.47_2.2.0"
SRCREV = "8bbe203ee7c1fc16198ce1bf964e54c90606c3c2"

SRC_URI = "git://github.com/nxp-imx/imx-jailhouse.git;protocol=ssh;branch=${SRCBRANCH} \
           file://0001-tools-scripts-update-shebang-to-python3.patch \
"

DEPENDS = " \
    make-native \
    python3-mako-native \
    python3-mako \
    dtc-native \
"

inherit module python3native bash-completion deploy setuptools3

S = "${WORKDIR}/git"
B = "${S}"

JH_ARCH = "arm64"
JH_DATADIR ?= "${datadir}/jailhouse"
CELL_DIR ?= "${JH_DATADIR}/cells"
CELLCONF_DIR ?= "${JH_DATADIR}/configs"
INMATES_DIR ?= "${JH_DATADIR}/inmates"

JH_CONFIG ?= "${S}/ci/jailhouse-config-x86.h"
JH_CONFIG_x86 ?= "${S}/ci/jailhouse-config-x86.h"
JH_CONFIG_x86-64 ?= "${S}/ci/jailhouse-config-x86.h"
JH_CONFIG_arm ?= "${S}/ci/jailhouse-config-banana-pi.h"

do_configure() {
   if [ -d ${STAGING_DIR_HOST}/${CELLCONF_DIR} ];
   then
      cp "${STAGING_DIR_HOST}/${CELLCONF_DIR}/"*.c ${S}/configs/${ARCH}/
   fi
}

USER_SPACE_CFLAGS = '${CFLAGS} -DLIBEXECDIR=\\\"${libexecdir}\\\" \
		  -DJAILHOUSE_VERSION=\\\"$JAILHOUSE_VERSION\\\" \
		  -Wall -Wextra -Wmissing-declarations -Wmissing-prototypes -Werror \
		  -I../driver'

TOOLS_SRC_DIR = "${S}/tools"
TOOLS_OBJ_DIR = "${S}/tools"

do_compile() {
    unset LDFLAGS
    oe_runmake V=1 CC="${CC}" \
        ARCH=${JH_ARCH} CROSS_COMPILE=${TARGET_PREFIX} \
        KDIR=${STAGING_KERNEL_BUILDDIR}

    cd ${TOOLS_SRC_DIR}
    export JAILHOUSE_VERSION=$(cat ../VERSION)
    oe_runmake V=1 \
        CFLAGS="${USER_SPACE_CFLAGS}" \
        src=${TOOLS_SRC_DIR} obj=${TOOLS_OBJ_DIR} \
        ${TOOLS_OBJ_DIR}/jailhouse-config-collect ${TOOLS_OBJ_DIR}/jailhouse
}

do_install() {
    oe_runmake \
        PYTHON=python3 \
        V=1 \
        LDFLAGS="" \
        CC="${CC}" \
        ARCH=${JH_ARCH} \
        CROSS_COMPILE=${TARGET_PREFIX} \
        KDIR=${STAGING_KERNEL_BUILDDIR} \
        DESTDIR=${D} install

    install -d ${D}${CELL_DIR}
    install ${B}/configs/${JH_ARCH}/*.cell ${D}${CELL_DIR}/

    install -d ${D}${INMATES_DIR}/tools/${JH_ARCH}
    install ${B}/inmates/demos/${JH_ARCH}/*.bin ${D}${INMATES_DIR}

    install -d ${D}${JH_DATADIR}/tools
    install ${B}/tools/jailhouse-cell-linux ${D}${JH_DATADIR}/tools
    install ${B}/tools/jailhouse-cell-stats ${D}${JH_DATADIR}/tools
    install ${B}/tools/jailhouse-config-collect ${D}${JH_DATADIR}/tools
    install ${B}/tools/jailhouse-config-create ${D}${JH_DATADIR}/tools
    install ${B}/tools/jailhouse-gcov-extract ${D}${JH_DATADIR}/tools
    install ${B}/tools/jailhouse-hardware-check ${D}${JH_DATADIR}/tools
    install ${B}/inmates/tools/${JH_ARCH}/linux-loader.bin ${D}${INMATES_DIR}/tools/${JH_ARCH}
}

PACKAGE_BEFORE_PN = "kernel-module-jailhouse pyjailhouse"

FILES_${PN} += "${base_libdir}/firmware ${libexecdir} ${sbindir} ${JH_DATADIR}"
FILES_pyjailhouse = "${PYTHON_SITEPACKAGES_DIR}/pyjailhouse"

RDEPENDS_${PN} += " \
    python3-curses \
    python3-datetime \
    python3-mmap \
"

RDEPENDS_pyjailhouse = " \
    python3-core \
    python3-ctypes \
    python3-fcntl \
    python3-shell \
"

INSANE_SKIP_${PN} = "ldflags"

COMPATIBLE_MACHINE = "(mx8m)"
