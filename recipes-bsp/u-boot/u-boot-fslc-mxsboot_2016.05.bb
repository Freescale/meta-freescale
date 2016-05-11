DESCRIPTION = "U-boot bootloader mxsboot tool"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"
SECTION = "bootloader"
DEPENDS = "openssl"
PROVIDES = "u-boot-mxsboot"

PV = "v2016.05+git${SRCPV}"

SRCREV = "0fcd279712bdc2f4860bf9466323eb6874d411ed"
SRCBRANCH = "2016.05+fslc"

SRC_URI = "git://github.com/Freescale/u-boot-fslc.git;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

inherit fsl-u-boot-localversion

EXTRA_OEMAKE = 'HOSTCC="${CC} ${CPPFLAGS}" HOSTLDFLAGS="-L${libdir} -L${base_libdir}" HOSTSTRIP=true CONFIG_MX28=y'

do_configure () {
    oe_runmake sandbox_defconfig
}

do_compile () {
    oe_runmake tools-only
}

do_install () {
    install -d ${D}${bindir}
    install -m 0755 tools/mxsboot ${D}${bindir}/uboot-mxsboot
    ln -sf uboot-mxsboot ${D}${bindir}/mxsboot
}

BBCLASSEXTEND = "native nativesdk"
