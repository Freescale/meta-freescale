require imx-gpu-viv-6.inc

SRC_URI[md5sum] = "f7b41c4f8bfa25959796d240ca0bb285"
SRC_URI[sha256sum] = "4a5f2235500dc4bdfaaeaffa0e4c13cdd28436cc0d859e5be222bce990fda461"

INSANE_SKIP:append:libc-musl = " file-rdeps"
RDEPENDS:${PN}:append:libc-musl = " gcompat"

COMPATIBLE_MACHINE = "(mx8-nxp-bsp)"
