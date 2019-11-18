FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# OpenGL is not required for parts with GPU support for 2D but not 3D
IMX_REQUIRED_DISTRO_FEATURES_REMOVE          = ""
IMX_REQUIRED_DISTRO_FEATURES_REMOVE_imxgpu2d = "opengl"
IMX_REQUIRED_DISTRO_FEATURES_REMOVE_imxgpu3d = ""
REQUIRED_DISTRO_FEATURES_remove = "${IMX_REQUIRED_DISTRO_FEATURES_REMOVE}"

SRC_URI_append_mx6sl = "file://weston.config"

# To customize weston.ini, start by setting the desired assignment in weston.ini,
# commented out. For example:
#     #xwayland=true
# Then add the assignment to INI_UNCOMMENT_ASSIGNMENTS.
INI_UNCOMMENT_ASSIGNMENTS_append_imx = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11 wayland', 'xwayland=true', '', d)} \
"
INI_UNCOMMENT_ASSIGNMENTS_append_mx7ulp = " \
    use-g2d=1 \
"
INI_UNCOMMENT_ASSIGNMENTS_append_mx8mm = " \
    use-g2d=1 \
"
INI_UNCOMMENT_ASSIGNMENTS_append_mx8mq = " \
    gbm-format=argb8888 \
    \\[shell\\] \
    size=1920x1080 \
"

uncomment() {
    if ! (grep "^#$1" $2); then
        bbfatal "Commented setting '#$1' not found in file $2"
    fi
    sed -i -e 's,^#'"$1"','"$1"',g' $2
}

do_install_append() {
    if [ -f "${WORKDIR}/weston.config" ]; then
        install -Dm0755 ${WORKDIR}/weston.config ${D}${sysconfdir}/default/weston
    fi
    for assignment in ${INI_UNCOMMENT_ASSIGNMENTS}; do
        uncomment "$assignment" ${D}${sysconfdir}/xdg/weston/weston.ini
    done
}
