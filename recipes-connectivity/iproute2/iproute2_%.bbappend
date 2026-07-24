do_install:append:imx-generic-bsp () {

    # Add tc folder headers
    install -d ${D}${includedir}/tc
    install -m 0644 ${B}/tc/*.h ${D}${includedir}/tc

    # Add include folder headers
    cp -R --no-preserve=ownership ${B}/include/* ${D}${includedir}
    # Remove files that conflict with glibc
    rm -f ${D}${includedir}/dlfcn.h
    rm -f ${D}${includedir}/netinet/tcp.h
    rm -f ${D}${includedir}/xtables.h
}

do_install:append:qoriq-generic-bsp () {

    # Add tc folder headers
    install -d ${D}${includedir}/tc
    install -m 0644 ${B}/tc/*.h ${D}${includedir}/tc

    # Add include folder headers
    cp -R --no-preserve=ownership ${B}/include/* ${D}${includedir}
    # Remove files that conflict with glibc
    rm -f ${D}${includedir}/dlfcn.h
    rm -f ${D}${includedir}/netinet/tcp.h
    rm -f ${D}${includedir}/xtables.h
}
