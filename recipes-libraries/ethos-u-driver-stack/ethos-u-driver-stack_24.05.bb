SUMMARY = "The Linux driver stack for Arm(R) Ethos(TM)-U NPU"
DESCRIPTION = "The Linux driver stack for Arm(R) Ethos(TM)-U provides \
               an example of how a rich operating system like Linux can dispatch \
               inferences to an Arm Cortex(R)-M subsystem, consisting of an Arm \
               Cortex-M of choice and an Arm Ethos-U NPU."
HOMEPAGE = "https://github.com/nxp-imx/ethos-u-driver-stack-imx"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=e3fc50a88d0a364313df4b21ef20c29e"

DEPENDS = "flatbuffers python3-pybind11 python3-pybind11-native"

SRC_URI = "${ETHOS_U_SRC};branch=${SRCBRANCH} \
           file://0001-ethosu.hpp-Fix-gcc15-build-issues.patch \
"

ETHOS_U_SRC ?= "git://github.com/nxp-imx/ethos-u-driver-stack-imx.git;protocol=https"
SRCBRANCH = "lf-6.18.2_1.0.0"
SRCREV = "94658d758ef1116e574aa7cf16f96c9546fe932e"

inherit cmake setuptools3

SETUPTOOLS_BUILD_ARGS = "build_ext --library-dirs ${B}/driver_library"

do_configure() {
    cmake_do_configure
}

do_compile () {
    cmake_do_compile
    setuptools3_do_compile
}

do_install () {
    cmake_do_install
    setuptools3_do_install
}

RDEPENDS:${PN} = "flatbuffers python3-numpy python3-pillow"

COMPATIBLE_MACHINE = "(mx93-nxp-bsp)"
