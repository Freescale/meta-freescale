SUMMARY = "Jailhouse, i.MX fork"
HOMEPAGE = "https://github.com/siemens/jailhouse"
SECTION = "jailhouse"
LICENSE = "GPL-2.0-only"

LIC_FILES_CHKSUM = "file://COPYING;md5=9fa7f895f96bde2d47fd5b7d95b6ba4d \
                 file://tools/root-cell-config.c.tmpl;beginline=6;endline=33;md5=2825581c1666c44a17955dc574cfbfb3 \
                 file://include/jailhouse/hypercall.h;beginline=9;endline=36;md5=2825581c1666c44a17955dc574cfbfb3 \
                 file://include/jailhouse/cell-config.h;beginline=9;endline=36;md5=2825581c1666c44a17955dc574cfbfb3 \
                 file://include/arch/arm/asm/jailhouse_hypercall.h;beginline=9;endline=36;md5=2825581c1666c44a17955dc574cfbfb3 \
                 file://include/arch/arm64/asm/jailhouse_hypercall.h;beginline=9;endline=36;md5=2825581c1666c44a17955dc574cfbfb3 \
                 file://include/arch/x86/asm/jailhouse_hypercall.h;beginline=9;endline=36;md5=2825581c1666c44a17955dc574cfbfb3 \
                 file://driver/jailhouse.h;beginline=9;endline=36;md5=2825581c1666c44a17955dc574cfbfb3 \
"

PROVIDES = "jailhouse"
RPROVIDES:${PN} += "jailhouse"

SRCBRANCH = "lf-6.6.3_1.0.0"
SRCREV = "184a287f4c4c63a3842a3b582b5d700e0f9fd9a4"

IMX_JAILHOUSE_SRC ?= "git://github.com/nxp-imx/imx-jailhouse.git;protocol=https"
SRC_URI = "${IMX_JAILHOUSE_SRC};branch=${SRCBRANCH} \
           file://arm-arm64-Makefile-Remove-march-option-from-Makefile.patch \
          "

DEPENDS = " \
    make-native \
    python3-mako-native \
    python3-mako \
    dtc-native \
"

inherit module bash-completion deploy setuptools3

S = "${WORKDIR}/git"
B = "${S}"

JH_ARCH = "arm64"
JH_DATADIR ?= "${datadir}/jailhouse"
CELL_DIR ?= "${JH_DATADIR}/cells"
CELLCONF_DIR ?= "${JH_DATADIR}/configs"
INMATES_DIR ?= "${JH_DATADIR}/inmates"

TUNE_CCARGS:remove:mx93-nxp-bsp = "-mcpu=cortex-a55"

EXTRA_OEMAKE += 'V=1'
EXTRA_OEMAKE += 'PYTHON=python3'
EXTRA_OEMAKE += 'LDFLAGS=""'
EXTRA_OEMAKE += 'CC="${CC}"'
EXTRA_OEMAKE += 'ARCH=${JH_ARCH}'
EXTRA_OEMAKE += 'CROSS_COMPILE=${TARGET_PREFIX}'
EXTRA_OEMAKE += 'KDIR=${STAGING_KERNEL_BUILDDIR}'
EXTRA_OEMAKE += 'MODLIB="${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}"'
EXTRA_OEMAKE += 'INSTALL_MOD_PATH=${D}${root_prefix}'
EXTRA_OEMAKE += 'firmwaredir=${nonarch_base_libdir}/firmware'

do_configure:prepend() {
   if [ -d ${STAGING_DIR_HOST}/${CELLCONF_DIR} ];
   then
      cp "${STAGING_DIR_HOST}/${CELLCONF_DIR}/"*.c ${S}/configs/${ARCH}/
   fi
}

do_compile:prepend() {
    # explicity call make to build the kernel module and tools
    oe_runmake
}

do_install:append() {
    oe_runmake DESTDIR=${D} install

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

PACKAGE_BEFORE_PN = "pyjailhouse"

FILES:${PN} += "${nonarch_base_libdir}/firmware ${libexecdir} ${sbindir} ${JH_DATADIR}"
# Remove libdir/* appended by setuptools3-base.bbclass for module split to work correctly
FILES:${PN}:remove = "${libdir}/*"
FILES:pyjailhouse = "${PYTHON_SITEPACKAGES_DIR}"

RDEPENDS:${PN} += " \
    pyjailhouse \
    python3-curses \
    python3-datetime \
    python3-mmap \
"

RDEPENDS:pyjailhouse = " \
    python3-core \
    python3-ctypes \
    python3-fcntl \
    python3-shell \
"

INSANE_SKIP:${PN} = "ldflags"

COMPATIBLE_MACHINE = "(mx8m-nxp-bsp|mx8ulp-nxp-bsp|mx9-nxp-bsp)"
