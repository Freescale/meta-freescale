SUMMARY = "Image Signal Processor (ISP) packages for i.MX"
DESCRIPTION = "Packages providing Image Signal Processor (ISP) support for i.MX, \
               including the ISP userspace library, Basler camera support and the \
               VVCAM kernel module."
SECTION = "multimedia"

# basler-camera* gets dynamically renamed
PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup

ISP_PKGS ?= ""
ISP_PKGS:mx8mp-nxp-bsp = "\
    isp-imx \
    basler-camera \
    kernel-module-isp-vvcam \
"
RDEPENDS:${PN} = "\
    ${ISP_PKGS} \
"
