DESCRIPTION = "Add packages for ISP build"

# basler-camera* gets dynamically renamed
PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup

ISP_PKGS      ?= ""
ISP_PKGS:mx8mp-nxp-bsp = " \
    isp-imx \
    basler-camera \
    kernel-module-isp-vvcam \
"
RDEPENDS:${PN} = " \
    ${ISP_PKGS} \
"
