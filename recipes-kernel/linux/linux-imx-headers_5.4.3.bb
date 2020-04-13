# Copyright 2017-2019 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Installs i.MX-specific kernel headers"
DESCRIPTION = "Installs i.MX-specific kernel headers to userspace. \
New headers are installed in ${includedir}/imx."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

SRCBRANCH = "lf-5.4.y"
LOCALVERSION = "-1.0.0"
SRC_URI = "git://source.codeaurora.org/external/imx/linux-imx.git;protocol=https;branch=${SRCBRANCH}"
SRCREV = "f8118585ee3c7025265b28985fdfe0af96a84466"

S = "${WORKDIR}/git"

do_configure[noexec] = "1"

do_compile[noexec] = "1"

IMX_UAPI_HEADERS = " \
    dma-buf.h \
    hantrodec.h \
    hx280enc.h \
    ion.h \
    ipu.h \
    isl29023.h \
    mxc_asrc.h \
    mxc_dcic.h \
    mxc_mlb.h \
    mxc_sim_interface.h \
    mxc_v4l2.h \
    mxcfb.h \
    pxp_device.h \
    pxp_dma.h \
    version.h \
    videodev2.h \
"

do_install() {
    # We install all headers inside of B so we can copy only the
    # whitelisted ones, and there is no risk of a new header to be
    # installed by mistake.
    oe_runmake headers_install INSTALL_HDR_PATH=${B}${exec_prefix}

    # Kernel should not be exporting this header
    rm -f ${D}${exec_prefix}/include/scsi/scsi.h

    # The ..install.cmd conflicts between various configure runs
    find ${D}${includedir} -name ..install.cmd | xargs rm -f

    # FIXME: The ion.h is still on staging so "promote" it for now
    cp ${S}/drivers/staging/android/uapi/ion.h ${B}${includedir}/linux

    # Install whitelisted headers only
    for h in ${IMX_UAPI_HEADERS}; do
        install -D -m 0644 ${B}${includedir}/linux/$h \
                       ${D}${includedir}/imx/linux/$h
    done
}

INHIBIT_DEFAULT_DEPS = "1"
DEPENDS += "unifdef-native bison-native rsync-native"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_HOST_imx = ".*"
COMPATIBLE_HOST ?= "(none)"
