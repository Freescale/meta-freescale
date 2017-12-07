SUMMARY = "Samples for OpenGL ES"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=aeb969185a143c3c25130bc2c3ef9a50"
DEPENDS = "virtual/libg2d imx-gpu-viv zlib libpng procps"

SRC_URI = "git://source.codeaurora.org/external/imx/apitrace-imx.git;protocol=https;branch=imx_7.1"
SRCREV = "759c5b7a004d1807e15a7f587023dc2e459b3509"

S = "${WORKDIR}/git"

inherit cmake lib_package pkgconfig perlnative pythonnative

EXTRA_OECMAKE += "-DENABLE_VIVANTE=ON -DENABLE_MULTIARCH=OFF"
EXTRA_OECMAKE += \
    "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '-DDISABLE_X11=ON', \
        bb.utils.contains('DISTRO_FEATURES',     'x11', '', \
                                                        '-DDISABLE_X11=ON', d), d)}"

FILES_${PN} = "${bindir} ${libdir}"
FILES_${PN}-dbg += "${libdir}/*/*/.debug"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(mx6q|mx6dl|mx6sx|mx6sl|mx7ulp)"
