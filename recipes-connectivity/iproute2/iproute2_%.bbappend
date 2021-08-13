do_install:append () {
    install -d ${D}/usr/include/tc 
    cp -a ${B}/include  ${D}/usr/include
    cp -a ${B}/tc/*.h    ${D}/usr/include/tc
}
