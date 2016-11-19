PACKAGECONFIG_remove_mx6 = " \
    ${@bb.utils.contains("DISTRO_FEATURES", "wayland", "x11", "", d)} \
"
