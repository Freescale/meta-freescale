# Copyright 2017-2024 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Installs i.MX-specific kernel headers"
DESCRIPTION = "Installs i.MX-specific kernel headers to userspace. \
New headers are installed in ${includedir}/imx."
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

SRC_URI = " \
    git://github.com/nxp-imx/linux-imx.git;protocol=https;branch=${SRCBRANCH} \
    file://0001-video-fbdev-mxc-make-uapi-ipu.h-pxp_dma.h-compatible.patch \
"
SRCBRANCH = "lf-6.12.y"
LOCALVERSION = "-lts-${SRCBRANCH}"
SRCREV = "37d02f4dcbbe6677dc9f5fc17f386c05d6a7bd7a"

do_configure[noexec] = "1"

do_compile[noexec] = "1"

IMX_UAPI_HEADERS = " \
    dma-buf.h \
    hantrodec.h \
    hx280enc.h \
    ipu.h \
    imx_vpu.h \
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

IMX_UAPI_HEADERS_SOUND = " \
    sound/compress_offload.h \
    sound/compress_params.h \
"

do_install() {
    # We install all headers inside of B so we can copy only the
    # i.MX-specific ones, and there is no risk of a new header to be
    # installed by mistake.
    oe_runmake headers_install INSTALL_HDR_PATH=${B}${exec_prefix}

    ################################################
    # BEGIN Copy of exceptional logic from linux-libc-headers
    # Kernel should not be exporting this header
    rm -f ${B}${exec_prefix}/include/scsi/scsi.h

    # The ..install.cmd conflicts between various configure runs
    find ${B}${includedir} -name ..install.cmd | xargs rm -f
    # END Copy from linux-libc-headers
    ################################################

    # Install i.MX-specific headers only
    for h in ${IMX_UAPI_HEADERS}; do
        install -D -m 0644 ${B}${includedir}/linux/$h \
                       ${D}${includedir}/imx/linux/$h
    done
    install -d ${D}${includedir}/imx/linux/sound
    for h in ${IMX_UAPI_HEADERS_SOUND}; do
        install -D -m 0644 ${B}${includedir}/$h \
                       ${D}${includedir}/imx/$h
    done
}

# Allow to build empty main package, this is required in order for -dev package
# to be propagated into the SDK
#
# Without this setting the RDEPENDS in other recipes fails to find this
# package, therefore causing the -dev package also to be skipped effectively not
# populating it into SDK
ALLOW_EMPTY:${PN} = "1"

INHIBIT_DEFAULT_DEPS = "1"
DEPENDS += "unifdef-native bison-native rsync-native"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

# Restrict this recipe to NXP BSP only, this recipe is not compatible
# with mainline BSP
COMPATIBLE_HOST = '(null)'
COMPATIBLE_HOST:use-nxp-bsp = '.*'
