# etnaviv mesa does not have glx
PACKAGECONFIG_remove_use-mainline-bsp = "xcomposite-glx"

PACKAGECONFIG_remove_mx6 = "xcomposite-egl xcomposite-glx"
PACKAGECONFIG_remove_mx7 = "xcomposite-egl xcomposite-glx"

# i.MX8 does never provide native x11, so required dependencies are not met
PACKAGECONFIG_remove_mx8 = "xcomposite-egl xcomposite-glx"
