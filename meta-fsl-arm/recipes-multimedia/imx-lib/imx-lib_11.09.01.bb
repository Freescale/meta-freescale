include imx-lib.inc

PR = "${INC_PR}.2"

SRC_URI += " file://0001-ENGR00156800-vpu-Fix-decoding-mp4PackedPBFrame-strea.patch \
             file://0002-ENGR00162690-vpu-Fix-the-issue-of-rotation-180-degre.patch"
SRC_URI[md5sum] = "45574f8f32f7000ca11d585fa60dea8c"
SRC_URI[sha256sum] = "f151a8bb3099b596b5834a1139c19e526802e6a0aa965018d16375e7e1f48f27"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx5)"
