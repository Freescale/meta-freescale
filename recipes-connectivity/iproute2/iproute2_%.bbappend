do_install:append:imx-generic-bsp () {
    install -d ${D}${includedir}/tc
    cp -R ${B}/include ${D}${includedir}
    install -m 0644 ${B}/tc/*.h ${D}${includedir}/tc
}

do_install:append:qoriq-generic-bsp () {
    install -d ${D}${includedir}/tc
    cp -R ${B}/include ${D}${includedir}
    install -m 0644 ${B}/tc/*.h ${D}${includedir}/tc
}
