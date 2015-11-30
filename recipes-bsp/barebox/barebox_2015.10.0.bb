require recipes-bsp/barebox/barebox.inc

SRC_URI = "http://barebox.org/download/${PN}-${PV}.tar.bz2 \
           file://defconfig"

SRC_URI[md5sum] = "50b787db3f91bebd5ed646e80a05caab"
SRC_URI[sha256sum] = "8353042fe27a30a01a00fe1e1f4a34f292a291027b9637d3ea8f305387407880"

COMPATIBLE_MACHINE = "Invalid!"
