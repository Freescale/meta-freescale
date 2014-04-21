#Freescale
include qt4-phonon-patches.inc

DEPENDS_append_mx5 = " virtual/kernel virtual/libgles2"
QT_GLFLAGS_mx5 = "-opengl es2 -openvg"
QT_CONFIG_FLAGS_append_mx5 = " -I${STAGING_KERNEL_DIR}/include/"

DEPENDS_append_mx6 = " virtual/kernel virtual/libgles2"
QT_GLFLAGS_mx6 = "-opengl es2 -openvg"
QT_CONFIG_FLAGS_append_mx6 = " -I${STAGING_KERNEL_DIR}/include/uapi \
                               -I${STAGING_KERNEL_DIR}/include/ \
                               -DLINUX=1 -DEGL_API_FB=1 \
                               -DQT_QPA_EXPERIMENTAL_TOUCHEVENT=1"

SRC_URI_append_mx6 = " file://0003-i.MX6-force-egl-visual-ID-33.patch"
