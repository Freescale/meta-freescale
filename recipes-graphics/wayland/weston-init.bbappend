FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# OpenGL is not required for parts with GPU support for 2D but not 3D
IMX_REQUIRED_DISTRO_FEATURES_REMOVE          = ""
IMX_REQUIRED_DISTRO_FEATURES_REMOVE_imxgpu2d = "opengl"
IMX_REQUIRED_DISTRO_FEATURES_REMOVE_imxgpu3d = ""
REQUIRED_DISTRO_FEATURES_remove = "${IMX_REQUIRED_DISTRO_FEATURES_REMOVE}"

SRC_URI += "${@bb.utils.contains('DISTRO_FEATURES', 'systemd wayland x11', 'file://weston.config', '', d)}"

HAS_SYSTEMD = "${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}"
HAS_XWAYLAND = "${@bb.utils.contains('DISTRO_FEATURES', 'wayland x11', 'true', 'false', d)}"

do_install_append() {
    if ${HAS_SYSTEMD}; then
        sed -i \
            -e 's,/usr/bin,${bindir},g' \
            -e 's,/etc,${sysconfdir},g' \
            -e 's,/var,${localstatedir},g' \
            ${D}${systemd_system_unitdir}/weston.service
        if ${HAS_XWAYLAND}; then
            install -Dm0755 ${WORKDIR}/weston.config ${D}${sysconfdir}/default/weston
        fi
    fi
}
