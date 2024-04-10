# Cairo 1.16.0 used in kirkstone, langdale, mickledore and nanbield still
# requires to replace opengl with the egl/gles for success building.
PACKAGECONFIG:append:imxgpu3d = " ${@bb.utils.contains('PV', '1.16.0', 'egl glesv2', '', d)}"
PACKAGECONFIG:remove:imxgpu3d = " ${@bb.utils.contains('PV', '1.16.0', 'opengl', '', d)}"

# links with imx-gpu libs which are pre-built for glibc
# gcompat will address it during runtime
LDFLAGS:append:imxgpu:libc-musl = " -Wl,--allow-shlib-undefined"

RDEPENDS:${PN}:append:imxgpu:libc-musl = " gcompat"
