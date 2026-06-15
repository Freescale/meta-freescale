SUMMARY = "The Linux driver stack for Arm(R) Ethos(TM)-U NPU"
DESCRIPTION = "The Linux driver stack for Arm(R) Ethos(TM)-U provides \
an example of how a rich operating system like Linux can dispatch \
inferences to an Arm Cortex(R)-M subsystem, consisting of an Arm \
Cortex-M of choice and an Arm Ethos-U NPU."
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=e3fc50a88d0a364313df4b21ef20c29e"

DEPENDS = "flatbuffers python3-pybind11 python3-pybind11-native"

SRC_URI = "${ETHOS_U_SRC};branch=${SRCBRANCH}"
ETHOS_U_SRC ?= "git://github.com/nxp-imx/ethos-u-driver-stack-imx.git;protocol=https"
SRCBRANCH = "lf-6.6.52_2.2.0"
SRCREV = "a80c51d77c91f1a8266afc5d8056fb981de4b504"

S = "${WORKDIR}/git"

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
