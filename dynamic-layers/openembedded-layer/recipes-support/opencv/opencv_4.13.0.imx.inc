# i.MX customization for opencv_4.13.0.imx.bb.
# Not a standalone recipe: required by that .bb, and must stay beside
# it. Keeping it separate lets the .bb remain a verbatim copy of the
# meta-openembedded original.

LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

# Need to override opencv and contrib URL because they include PV
IMX_BASE_VERSION = "${@'.'.join((d.getVar('PV') or '').split('.')[:3])}"
SRC_URI:remove = "\
    git://github.com/opencv/opencv.git;name=opencv;branch=4.x;protocol=https;tag=${PV} \
    git://github.com/opencv/opencv_contrib.git;destsuffix=${BB_GIT_DEFAULT_DESTSUFFIX}/contrib;name=contrib;branch=4.x;protocol=https;tag=${PV}"
SRC_URI:prepend = "\
    git://github.com/opencv/opencv.git;name=opencv;branch=4.x;protocol=https;tag=${IMX_BASE_VERSION} \
    git://github.com/opencv/opencv_contrib.git;destsuffix=${BB_GIT_DEFAULT_DESTSUFFIX}/contrib;name=contrib;branch=4.x;protocol=https;tag=${IMX_BASE_VERSION} "

# i.MX patches
#
# 0107 and 0108 carry no Signed-off-by. They came in from upstream review
# rather than from this layer, and a sign-off is a certification only their
# author can make, so there is nothing here for us to correct. Note this
# covers the whole block: a patch added below without a sign-off will not be
# reported either.
# nooelint: oelint.file.patchsignedoff
SRC_URI += "\
    file://0101-MGS-6470-ccc-Modify-host-ptr-alignment-size-in-UMAT.patch \
    file://0102-MGS-6470-ccc-Add-configuration-parameter-to-force-en.patch \
    file://0103-MGS-6470-ccc-Change-configuration-to-enable-hostptr-.patch \
    file://0104-MGS-8011-ccc-Fix-the-problem-of-syntax-error-at-doub.patch \
    file://0105-MGS-8318-ccc-Fix-error-implicit-declaration-of-funct.patch \
    file://0106-core-opencl-fix-inplace-transpose-race-by-enforcing-.patch \
    file://0107-imgproc-perf-HoughLines-Fix-test-tolerance-and-compa.patch \
    file://0108-imgproc-perf-HoughLines-Fix-lower-bound-for-line-cou.patch \
    file://0109-core-ocl-fix-incorrect-results-for-in-place-flip-on-.patch \
"

# Add opencv_extra
SRC_URI += "\
    git://github.com/opencv/opencv_extra.git;destsuffix=extra;name=extra;branch=4.x;protocol=https \
    file://0001-Add-smaller-version-of-download_models.py.patch;patchdir=${UNPACKDIR}/extra \
"
# SRCREV_FORMAT is an underscore-joined list of SRC_URI names; the appended
# component must attach directly with no leading space.
# nooelint: oelint.vars.inconspaces
SRCREV_FORMAT:append = "_extra"
SRCREV_extra = "b6db059e9b80072d80d009d2ab344f8606a8e964"

# Patch DNN example
SRC_URI += "\
    file://OpenCV_DNN_examples.patch \
"

PACKAGECONFIG:remove = "eigen"

PACKAGECONFIG:append = " \
    dnn \
    text \
    ${@bb.utils.contains('BBFILE_COLLECTIONS', 'qt6-layer', 'qt6', '', d)} \
    ${PACKAGECONFIG_OPENCL} \
"

PACKAGECONFIG_OPENCL = ""
PACKAGECONFIG_OPENCL:imxgpu = "opencl"
PACKAGECONFIG_OPENCL:mx8mm-nxp-bsp = ""

PACKAGECONFIG[openvx] = "-DWITH_OPENVX=ON -DOPENVX_ROOT=${STAGING_LIBDIR} -DOPENVX_LIB_CANDIDATES='OpenVX;OpenVXU',-DWITH_OPENVX=OFF,virtual/libopenvx,"
PACKAGECONFIG[qt5] = "-DWITH_QT=ON -DOE_QMAKE_PATH_EXTERNAL_HOST_BINS=${STAGING_BINDIR_NATIVE} -DCMAKE_PREFIX_PATH=${STAGING_BINDIR_NATIVE}/cmake,-DWITH_QT=OFF,qtbase qtbase-native,"
PACKAGECONFIG[qt6] = "-DWITH_QT=ON -DQT_HOST_PATH=${RECIPE_SYSROOT_NATIVE}${prefix_native},-DWITH_QT=OFF,qtbase qtbase-native qt5compat,"
PACKAGECONFIG[tests-imx] = "-DINSTALL_TESTS=ON -DOPENCV_TEST_DATA_PATH=${UNPACKDIR}/extra/testdata, -DINSTALL_TESTS=OFF,"
PACKAGECONFIG[tim-vx] = "-DWITH_TIMVX=ON -DTIMVX_INSTALL_DIR=${STAGING_DIR_HOST}${libdir},-DWITH_TIMVX=OFF,tim-vx"

do_install:append() {
    ln -sf opencv4/opencv2 ${D}${includedir}/opencv2
    install -d ${D}${datadir}/opencv4/samples/data
    cp -r ${S}/samples/data/* ${D}${datadir}/opencv4/samples/data
    install -d ${D}${datadir}/opencv4/samples/bin/
    install -m 0755 bin/example_* ${D}${datadir}/opencv4/samples/bin/
    if ${@bb.utils.contains('PACKAGECONFIG', 'tests-imx', 'true', 'false', d)}; then
        cp -r share/opencv4/testdata/cv/face/* ${D}${datadir}/opencv4/testdata/cv/face/
    fi
    # Rename cpp folder to avoid collision with GCC /usr/bin/cpp.
    mv ${D}${bindir}/cpp ${D}${bindir}/opencv_cpp
}

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

COMPATIBLE_MACHINE = "(mx8-nxp-bsp|mx9-nxp-bsp)"
