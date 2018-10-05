# Copyright 2017-2018 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Installs i.MX-specific kernel headers"
DESCRIPTION = "Installs i.MX-specific kernel headers to userspace. \
New headers are installed in ${includedir}/imx."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRCBRANCH = "imx_4.9.123_imx8mm_ga"
LOCALVERSION = "-${SRCBRANCH}"
SRC_URI = "git://source.codeaurora.org/external/imx/linux-imx.git;protocol=https;branch=${SRCBRANCH} \
    file://0001-uapi-Install-custom-headers.patch"
SRCREV = "6a71cbc089755afd6a86c005c22a1af6eab24a70"
S = "${WORKDIR}/git"

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
    oe_runmake headers_install INSTALL_HDR_PATH=${B}${exec_prefix}
    cp ${S}/drivers/staging/android/uapi/ion.h ${B}${includedir}/linux
    src=${B}${includedir}/linux
    dest=${D}${includedir}/imx/linux
    install -d $dest
    cd $src
    install -m 0644 ${IMX_UAPI_HEADERS} $dest
    cd -
}

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
