DESCRIPTION = "User space application for Pattern Matcher"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://EULA;md5=c9ae442cf1f9dd6c13dfad64b0ffe73f"

DEPENDS="libedit"
RDEPENDS_${PN} += "bash"

SRC_URI = "git://git.freescale.com/ppc/sdk/fsl-pme.git;branch=sdk-v2.0.x"
SRCREV = "f49fb57960ff5ee649be97a9587ede88e433ea71"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CC="${CC}" AR="${AR}" SYS_PATH=${STAGING_LIBDIR}/..'
do_install() {
    install -d ${D}${datadir}/fsl-pme
    install -d ${D}${base_sbindir}
    install -d ${D}${sysconfdir}/hotplug
    install bin/* ${D}${base_sbindir}/
    install -m 644 sample* ${D}${datadir}/fsl-pme/
    install -m 644 modprobe.conf ${D}${sysconfdir}
    install -m 755 misc.agent ${D}${sysconfdir}/hotplug
}

FILES_${PN} += "${datadir}/*"
INSANE_SKIP_${PN} += "ldflags"

COMPATIBLE_MACHINE = "(e500v2)"
PACKAGE_ARCH = "${MACHINE_SOCARCH}"

