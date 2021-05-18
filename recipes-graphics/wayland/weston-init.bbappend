FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# OpenGL is not required for parts with GPU support for 2D but not 3D
IMX_REQUIRED_DISTRO_FEATURES_REMOVE          = ""
IMX_REQUIRED_DISTRO_FEATURES_REMOVE_imxgpu2d = "opengl"
IMX_REQUIRED_DISTRO_FEATURES_REMOVE_imxgpu3d = ""
REQUIRED_DISTRO_FEATURES_remove = "${IMX_REQUIRED_DISTRO_FEATURES_REMOVE}"

SRC_URI_append_mx6sl = " file://weston.config"

# To customize weston.ini, start by setting the desired assignment in weston.ini,
# commented out. For example:
#     #xwayland=true
# Then add the assignment to INI_UNCOMMENT_ASSIGNMENTS.
INI_UNCOMMENT_ASSIGNMENTS_append_imx = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11 wayland', 'xwayland=true', '', d)} \
"
INI_UNCOMMENT_ASSIGNMENTS_append_mx8 = " \
    repaint-window=16 \
"
INI_UNCOMMENT_ASSIGNMENTS_append_mx8mq = " \
    gbm-format=argb8888 \
    \\[shell\\] \
    size=1920x1080 \
"

# FIXME: The 8QM and 8QXP SoCs have better performance without G2D so don't enable it
# Ideally, this should be seamless and Vivante ought to handle it internally and take the fastest
# rendering code.
INI_UNCOMMENT_USE_G2D_imxgpu2d ?= "use-g2d=1"
INI_UNCOMMENT_USE_G2D_mx8qm = ""
INI_UNCOMMENT_USE_G2D_mx8qxp = ""
INI_UNCOMMENT_ASSIGNMENTS_append_imxgpu2d = " \
    ${INI_UNCOMMENT_USE_G2D} \
"

uncomment() {
    if ! grep -q "^#$1" $2 && ! grep -q "^$1" $2; then
        bbwarn "Commented setting '#$1' not found in file $2"
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
