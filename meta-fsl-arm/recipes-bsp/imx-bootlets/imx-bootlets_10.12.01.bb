DESCRIPTION = "i.MXS boot streams"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PR = "r5"

SRC_URI = "http://download.ossystems.com.br/bsp/freescale/source/imx-bootlets-src-${PV}.tar.gz \
           file://linux-fix-paths.patch \
           file://linux_prep-fix-cmdlines.patch \
           file://add-command-script-for-barebox.patch"

SRC_URI[md5sum] = "cf0ab3822dca694b930a051501c1d0e4"
SRC_URI[sha256sum] = "63f6068ae36884adef4259bbb1fe2591755718f22c46d0a59d854883dfab1ffc"

S = "${WORKDIR}/imx-bootlets-src-${PV}"

inherit deploy

# Disable parallel building or it may fail to build.
PARALLEL_MAKE = ""

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX}"

# Ensure machine defines the IMXBOOTLETS_MACHINE
python () {
    if not d.getVar("IMXBOOTLETS_MACHINE", True):
        PN = d.getVar("PN", True)
        FILE = os.path.basename(d.getVar("FILE", True))
        bb.debug(1, "To build %s, see %s for instructions on \
                     setting up your machine config" % (PN, FILE))
        raise bb.parse.SkipPackage("because IMXBOOTLETS_MACHINE is not set")
}

do_configure () {
    # Use machine specific binaries
    sed 's,@MACHINE@,${MACHINE},g;s,@DTB@,-dtb,g' < linux.bd > linux.bd-dtb
    sed -i 's,@MACHINE@,${MACHINE},g;s,@DTB@,,g' linux.bd
    sed -i 's,@MACHINE@,${MACHINE},g' barebox_ivt.bd
}

do_compile () {
    oe_runmake BOARD=${IMXBOOTLETS_MACHINE} linux_prep \
                                            boot_prep \
                                            power_prep \
               'CC=${TARGET_PREFIX}gcc --sysroot="${STAGING_DIR_TARGET}"' \
               'LD=${TARGET_PREFIX}ld --sysroot="${STAGING_DIR_TARGET}"'
}

do_install () {
    install -d ${D}/boot/
    install -m 644 boot_prep/boot_prep power_prep/power_prep \
                   linux_prep/output-target/linux_prep \
                   linux.bd linux.bd-dtb \
                   barebox_ivt.bd \
                   ${D}/boot
}

FILES_${PN} = "/boot"

do_deploy () {
    install -d ${DEPLOYDIR}

    for f in boot_prep/boot_prep \
         power_prep/power_prep \
         linux_prep/output-target/linux_prep \
         barebox_ivt.bd \
         linux.bd linux.bd-dtb; do
        full_name="imx-bootlets-`basename $f`-${MACHINE}-${PV}-${PR}"
        symlink_name="imx-bootlets-`basename $f`-${MACHINE}"

        install -m 644 ${S}/$f ${DEPLOYDIR}/$full_name
        (cd ${DEPLOYDIR} ; rm -f $symlink_name ; ln -sf $full_name $symlink_name)
    done
}

addtask deploy before do_build after do_compile

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mxs)"
