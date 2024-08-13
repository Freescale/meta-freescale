# etnaviv mesa does not have glx
PACKAGECONFIG:remove:use-mainline-bsp = "xcomposite-glx"

PACKAGECONFIG:remove:mx6-nxp-bsp = "xcomposite-egl xcomposite-glx"
PACKAGECONFIG:remove:mx7-nxp-bsp = "xcomposite-egl xcomposite-glx"
PACKAGECONFIG:remove:mx8-nxp-bsp = "xcomposite-egl xcomposite-glx"
