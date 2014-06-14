DESCRIPTION = "SEC user space driver package"
LICENSE = "BSD GPLv2"

DEPENDS += "flib usdpaa"

# no COPYING file in current git tree, need to be fixed
LIC_FILES_CHKSUM = "file://Makefile;endline=30;md5=83b6209ab517640a7390536a08d33609"

SRC_URI = "git://git.freescale.com/ppc/sdk/uspdcp.git;nobranch=1"
SRCREV = "30016cdf36553c14f7143ef005a4925edd901fb0"

S = "${WORKDIR}/git"

EXTRA_OEMAKE="ARCH=${TARGET_ARCH} EXTRA_DEFINE='USDPAA CONFIG_PHYS_64BIT' CROSS_COMPILE=${TARGET_PREFIX} SDK_DIR=${STAGING_DIR}/${MACHINE}"

do_install(){
    oe_runmake install DESTDIR=${D}
}
