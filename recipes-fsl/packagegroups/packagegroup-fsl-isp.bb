DESCRIPTION = "Add packages for ISP build"

inherit packagegroup

ISP_PKGS      ?= ""
ISP_PKGS_mx8mp = " \
    isp-imx \
    basler-camera \
    basler-camera-dev \
    kernel-module-isp-vvcam \
"
RDEPENDS_${PN} = " \
    ${ISP_PKGS} \
"
