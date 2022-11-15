require imx-gpu-viv-6.inc

SRC_URI[md5sum] = "b899f9d4a179eab622880fd22ec7cb64"
SRC_URI[sha256sum] = "5c52bd15146c24d449638c6276c07103949c8efbc53d002518541bc37c57e424"

INSANE_SKIP:append:libc-musl = " file-rdeps"

COMPATIBLE_MACHINE = "(mx8-nxp-bsp)"

RDEPENDS:${PN}:append:libc-musl = " gcompat"
