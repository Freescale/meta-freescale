PACKAGECONFIG_remove_mx6 = "${@base_contains("DISTRO_FEATURES", "x11", "wayland", "", d)}"
