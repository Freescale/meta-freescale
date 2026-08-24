# Shared i.MX support fragment, only ever included by qt4 bbappends.
# Standard bbappend idiom: cannot carry an override, and FILESEXTRAPATHS is
# in BB_BASEHASH_IGNORE_VARS, so it cannot reach a task signature.
# nooelint: oelint.vars.noncoreoverride
FILESEXTRAPATHS:prepend := "${THISDIR}/qt4:"

DEPENDS:append:imxgpu2d = " virtual/kernel virtual/libgles2"

# Kernel shared-workdir dependency is only needed on mx6; keep it as an
# anonymous python guard on MACHINEOVERRIDES families.
# oelint.vars.noncoreoverride also fires here and is not suppressed: for an
# anonymous python block oelint anchors the finding to the preceding line, so a
# directive there only moves the anchor onto itself. The handler tests
# MACHINEOVERRIDES for the mx6 family first, so it is a no-op elsewhere.
# nooelint: oelint.task.noanonpython
python __anonymous () {
    families = ['mx6']
    cur_families = (d.getVar('MACHINEOVERRIDES') or '').split(':')
    if any(map(lambda x: x in cur_families,
               families)):
        d.appendVarFlag('do_configure', 'depends', ' virtual/kernel:do_shared_workdir')
}

SRC_URI:append:imxgpu2d = " \
    file://0001-Add-support-for-i.MX-codecs-to-phonon.patch \
    file://0002-i.MX-video-renderer-Allow-v4l-device-from-environmen.patch \
    file://0003-i.MX6-force-egl-visual-ID-33.patch \
    file://0001-config.tests-add-DEFINES-to-compile-egl-test-with-im.patch \
    file://0002-config.tests-add-DEFINES-to-compile-egl4gles1-test-w.patch \
"
QT_GLFLAGS:imxgpu2d = "-opengl es2 -openvg"
QT_CONFIG_FLAGS:append:imxgpu2d = " \
    -I${STAGING_KERNEL_DIR}/include/uapi \
    -I${STAGING_KERNEL_DIR}/include/ \
    -DLINUX=1 -DEGL_API_FB=1 \
    -DQT_QPA_EXPERIMENTAL_TOUCHEVENT=1"

# The QT_CONFIG_FLAGS can pollute *.la files with -Dxxx
do_compile:append:mx6-nxp-bsp () {
    find lib -name "*.la" | xargs -n1 sed -i 's/-D.*=1//g'
}

