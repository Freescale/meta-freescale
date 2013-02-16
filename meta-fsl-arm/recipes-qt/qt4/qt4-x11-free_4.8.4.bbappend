#Freescale
FILESEXTRAPATHS_prepend := "${THISDIR}/qt4:"

SRC_URI_append_mx5 += "file://0001-Add-support-for-i.MX-codecs-to-phonon.patch"
SRC_URI_append_mx6 += "file://0001-Add-support-for-i.MX-codecs-to-phonon.patch"

DEPENDS_append_mx5 = " virtual/kernel virtual/libgles2"
PACKAGE_ARCH_mx5 = "${MACHINE_ARCH}"
QT_GLFLAGS_mx5 = "-opengl es2 -openvg"
QT_CONFIG_FLAGS_append_mx5 = " -I${STAGING_KERNEL_DIR}/include/"

DEPENDS_append_mx6 = " virtual/kernel"
PACKAGE_ARCH_mx6 = "${MACHINE_ARCH}"
QT_CONFIG_FLAGS_append_mx6 = " -I${STAGING_KERNEL_DIR}/include/"

PRINC := "${@int(PRINC) + 2}"
