require imx-gpu-viv-6.inc

SRC_URI[md5sum] = "b33fde69f544674340ea617e9faf404e"
SRC_URI[sha256sum] = "60a63b66ef28cadc62bc1305a3ab5a2018c910e171b0cd72f4876317282dcfcc"

INSANE_SKIP:append:libc-musl = " file-rdeps"

COMPATIBLE_MACHINE = "(mx8-nxp-bsp)"

RDEPENDS:${PN}:append:libc-musl = " gcompat"
