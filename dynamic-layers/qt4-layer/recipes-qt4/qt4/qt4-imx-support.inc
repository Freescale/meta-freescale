FILESEXTRAPATHS:prepend := "${THISDIR}/qt4:"

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

DEPENDS:append:imxgpu2d = " virtual/kernel virtual/libgles2"
QT_GLFLAGS:imxgpu2d = "-opengl es2 -openvg"
QT_CONFIG_FLAGS:append:imxgpu2d = " -I${STAGING_KERNEL_DIR}/include/uapi \
                               -I${STAGING_KERNEL_DIR}/include/ \
                               -DLINUX=1 -DEGL_API_FB=1 \
                               -DQT_QPA_EXPERIMENTAL_TOUCHEVENT=1"

# The QT_CONFIG_FLAGS can pollute *.la files with -Dxxx
do_compile:append:mx6-nxp-bsp () {
    find lib -name "*.la" | xargs -n1 sed -i 's/-D.*=1//g'
}

