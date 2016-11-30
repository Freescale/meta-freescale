SUMMARY = "Linux IPC Userspace Tool"
DESCRIPTION = "DSP boot application and ipc test application"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=fa38cd73d71527dc6efb546474f64d10"

SRC_URI = "git://git.freescale.com/ppc/sdk/ipc.git;branch=sdk-v2.0.x \
    file://Makefile-use-LDFLAGS-if-set.patch \
"
SRCREV = "74d662707558290f070f9589177db730444bc435"

S = "${WORKDIR}/git"

# workaround for issue of parallel build, required a actual fix in ipc source
PARALLEL_MAKE = ""

EXTRA_OEMAKE = 'CROSS_COMPILE="${TARGET_PREFIX}" CC="${CC}" AR="${AR}" B4860=1'

do_install () {
    install -d ${D}${bindir}
    install -d ${D}${includedir}
    install -d ${D}/ipc
    install -m 755 ${S}/dsp_boot/dsp_bt ${D}/ipc
    install -m 755 ${S}/ipc/ipc_test ${D}/ipc
    install -m 755 ${S}/ipc/ipc_test67 ${D}/ipc
    install -m 755 ${S}/ipc/l1d_app ${D}/ipc
    install -m 755 ${S}/fsl_shm/app ${D}${bindir}/lg_shm_test
    install -d ${D}${base_libdir}
    install -m 755 ${S}/ipc/libipc.so ${D}${base_libdir}
    install -m 755 ${S}/ipc/libmem.so ${D}${base_libdir}
    install -m 755 ${S}/ipc/libdspboot.so  ${D}${base_libdir}
    install -d ${D}${includedir}/ipc
    install -d ${D}${includedir}/ipc/ipc/include
    install -d ${D}${includedir}/ipc/fsl_shm/lib
    install ${S}/ipc/include/*.h ${D}${includedir}/ipc/ipc/include
    install ${S}/dsp_boot/*.h ${D}${includedir}/ipc/ipc/include
    install ${S}/kernel/fsl_ipc_types.h ${D}${includedir}/ipc/ipc/include
    install ${S}/kernel/fsl_heterogeneous_common.h ${D}${includedir}/ipc/ipc/include
    install ${S}/kernel/fsl_heterogeneous_l1_defense.h ${D}${includedir}/ipc/ipc/include
    install ${S}/fsl_shm/include/*.h ${D}${includedir}/ipc/ipc/include
    install ${S}/fsl_shm/lib/*.h ${D}${includedir}/ipc/fsl_shm/lib
}

FILES_${PN} += "${base_libdir}/*.so /ipc/*"
FILES_${PN}-dev = "${includedir}"
FILES_${PN}-dbg += "/ipc/.debug"

INSANE_SKIP_${PN} += "file-rdeps"
INSANE_SKIP_${PN}-dev += "dev-elf"

COMPATIBLE_MACHINE = "(b4860qds|b4420qds)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

