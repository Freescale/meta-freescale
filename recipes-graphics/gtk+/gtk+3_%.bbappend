PACKAGECONFIG_remove_mx6 = "${@bb.utils.contains("DISTRO_FEATURES", "x11", "wayland", "", d)}"

