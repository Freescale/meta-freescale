RDEPENDS:${PN} += "\
    nativesdk-imx-usb-loader \
    nativesdk-mxsldr \
    nativesdk-u-boot-mkimage \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'nativesdk-wayland', '', d)} \
"
