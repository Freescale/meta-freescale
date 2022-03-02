# etnaviv mesa does not have glx
PACKAGECONFIG:remove:use-mainline-bsp = "xcomposite-glx"

PACKAGECONFIG:remove:mx6-nxp-bsp = "xcomposite-egl xcomposite-glx"
PACKAGECONFIG:remove:mx7-nxp-bsp = "xcomposite-egl xcomposite-glx"

# i.MX8 does never provide native x11, so required dependencies are not met
PACKAGECONFIG:remove:mx8-nxp-bsp = "xcomposite-egl xcomposite-glx"
