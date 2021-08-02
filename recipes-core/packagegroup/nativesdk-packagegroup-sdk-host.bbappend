RDEPENDS:${PN} += " \
    nativesdk-mxsldr \
    nativesdk-u-boot-mkimage \
    nativesdk-imx-usb-loader \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'nativesdk-wayland', '', d)} \
"
