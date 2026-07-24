do_install:append:qoriq-generic-bsp () {

    # Add tc folder headers
    install -d ${D}${includedir}/tc
    install -m 0644 ${B}/tc/*.h ${D}${includedir}/tc

    # Add include folder headers at /usr/include/include
    cp -R --no-preserve=ownership ${B}/include ${D}${includedir}
}
