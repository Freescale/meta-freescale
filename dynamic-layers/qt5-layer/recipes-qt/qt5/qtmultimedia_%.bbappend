# Fixes QT5 application builds searching for libimx6vivantevideonode.so
# via cmake cmake/Qt5Multimedia/Qt5MultimediaConfig.cmake
do_install:append:imxgpu() {
    install -d ${D}${libdir}/plugins/videoimx6vivantevideonode
    ln -sf ../video/videonode/libeglvideonode.so ${D}${libdir}/plugins/videoimx6vivantevideonode/libeglvideonode.so
    ln -sf ../video/videonode/libimx6vivantevideonode.so ${D}${libdir}/plugins/videoimx6vivantevideonode/libimx6vivantevideonode.so
}

# The compatibility symlinks installed above ship an unversioned .so in the
# runtime -plugins package, so the dev-so QA check does not apply.
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN}-plugins:imxgpu = "dev-so"
