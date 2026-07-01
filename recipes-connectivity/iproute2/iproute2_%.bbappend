do_install:append:imx-generic-bsp () {
    install -d ${D}/usr/include/tc
    cp -a ${B}/include  ${D}/usr/include
    cp -a ${B}/tc/*.h    ${D}/usr/include/tc
}

do_install:append:qoriq-generic-bsp () {
    install -d ${D}/usr/include/tc
    cp -a ${B}/include  ${D}/usr/include
    cp -a ${B}/tc/*.h    ${D}/usr/include/tc
}
