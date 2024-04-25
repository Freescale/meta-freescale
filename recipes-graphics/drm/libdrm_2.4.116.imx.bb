SUMMARY = "Userspace interface to the kernel DRM services"
DESCRIPTION = "The runtime library for accessing the kernel DRM services.  DRM \
stands for \"Direct Rendering Manager\", which is the kernel portion of the \
\"Direct Rendering Infrastructure\" (DRI).  DRI is required for many hardware \
accelerated OpenGL drivers."
HOMEPAGE = "http://dri.freedesktop.org"
SECTION = "x11/base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9eb1f4831351ab42d762c40b3ebb7add \
		    file://xf86drm.c;beginline=9;endline=32;md5=c8a3b961af7667c530816761e949dc71"
PROVIDES = "drm"
DEPENDS = "libpthread-stubs"

SRC_URI = "${IMX_LIBDRM_SRC};branch=${SRCBRANCH}"
IMX_LIBDRM_SRC ?= "git://github.com/nxp-imx/libdrm-imx.git;protocol=https"
SRCBRANCH = "libdrm-imx-2.4.116"
SRCREV = "2f1797674f94572ae8c365c8cbffb0263337ed57"

S = "${WORKDIR}/git"

DEFAULT_PREFERENCE = "-1"
COMPATIBLE_MACHINE = "(imx-nxp-bsp)"

inherit meson pkgconfig manpages

PACKAGECONFIG ??= "intel radeon amdgpu nouveau vmwgfx omap freedreno vc4 etnaviv tests install-test-programs"
PACKAGECONFIG[intel] = "-Dintel=enabled,-Dintel=disabled,libpciaccess"
PACKAGECONFIG[radeon] = "-Dradeon=enabled,-Dradeon=disabled"
PACKAGECONFIG[amdgpu] = "-Damdgpu=enabled,-Damdgpu=disabled"
PACKAGECONFIG[nouveau] = "-Dnouveau=enabled,-Dnouveau=disabled"
PACKAGECONFIG[vmwgfx] = "-Dvmwgfx=enabled,-Dvmwgfx=disabled"
PACKAGECONFIG[omap] = "-Domap=enabled,-Domap=disabled"
PACKAGECONFIG[exynos] = "-Dexynos=enabled,-Dexynos=disabled"
PACKAGECONFIG[freedreno] = "-Dfreedreno=enabled,-Dfreedreno=disabled"
PACKAGECONFIG[tegra] = "-Dtegra=enabled,-Dtegra=disabled"
PACKAGECONFIG[vc4] = "-Dvc4=enabled,-Dvc4=disabled"
PACKAGECONFIG[etnaviv] = "-Detnaviv=enabled,-Detnaviv=disabled"
PACKAGECONFIG[freedreno-kgsl] = "-Dfreedreno-kgsl=true,-Dfreedreno-kgsl=false"
PACKAGECONFIG[valgrind] = "-Dvalgrind=enabled,-Dvalgrind=disabled,valgrind"
PACKAGECONFIG[install-test-programs] = "-Dinstall-test-programs=true,-Dinstall-test-programs=false"
PACKAGECONFIG[cairo-tests] = "-Dcairo-tests=enabled,-Dcairo-tests=disabled"
PACKAGECONFIG[tests] = "-Dtests=true,-Dtests=false"
PACKAGECONFIG[udev] = "-Dudev=true,-Dudev=false,udev"
PACKAGECONFIG[manpages] = "-Dman-pages=enabled,-Dman-pages=disabled,libxslt-native xmlto-native python3-docutils-native"

ALLOW_EMPTY:${PN}-drivers = "1"
PACKAGES =+ "${PN}-tests ${PN}-drivers ${PN}-radeon ${PN}-nouveau ${PN}-omap \
             ${PN}-intel ${PN}-exynos ${PN}-freedreno ${PN}-amdgpu \
             ${PN}-etnaviv"

RRECOMMENDS:${PN}-drivers = "${PN}-radeon ${PN}-nouveau ${PN}-omap ${PN}-intel \
                             ${PN}-exynos ${PN}-freedreno ${PN}-amdgpu \
                             ${PN}-etnaviv"

FILES:${PN}-tests = "${bindir}/*"
FILES:${PN}-radeon = "${libdir}/libdrm_radeon.so.*"
FILES:${PN}-nouveau = "${libdir}/libdrm_nouveau.so.*"
FILES:${PN}-omap = "${libdir}/libdrm_omap.so.*"
FILES:${PN}-intel = "${libdir}/libdrm_intel.so.*"
FILES:${PN}-exynos = "${libdir}/libdrm_exynos.so.*"
FILES:${PN}-freedreno = "${libdir}/libdrm_freedreno.so.*"
FILES:${PN}-amdgpu = "${libdir}/libdrm_amdgpu.so.* ${datadir}/${PN}/amdgpu.ids"
FILES:${PN}-etnaviv = "${libdir}/libdrm_etnaviv.so.*"

BBCLASSEXTEND = "native nativesdk"

PACKAGES:prepend:imxgpu = "${PN}-vivante "
RRECOMMENDS:${PN}-drivers:append:imxgpu = " ${PN}-vivante"
FILES:${PN}-vivante = "${libdir}/libdrm_vivante.so.*"
PACKAGECONFIG:append:imxgpu = " vivante"
PACKAGECONFIG[vivante] = "-Dvivante=true,-Dvivante=false"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
