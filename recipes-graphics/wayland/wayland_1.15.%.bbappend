# Remove files provided by imx-gpu-viv
do_install_append_imxgpu () {
    rm -f ${D}${libdir}/pkgconfig/wayland-egl.pc
}
