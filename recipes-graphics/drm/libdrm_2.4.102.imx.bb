SUMMARY = "Userspace interface to the kernel DRM services"
DESCRIPTION = "The runtime library for accessing the kernel DRM services.  DRM \
stands for \"Direct Rendering Manager\", which is the kernel portion of the \
\"Direct Rendering Infrastructure\" (DRI).  DRI is required for many hardware \
accelerated OpenGL drivers."
HOMEPAGE = "http://dri.freedesktop.org"
SECTION = "x11/base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://xf86drm.c;beginline=9;endline=32;md5=c8a3b961af7667c530816761e949dc71"
PROVIDES = "drm"
DEPENDS = "libpthread-stubs"

IMX_LIBDRM_SRC ?= "git://github.com/nxp-imx/libdrm-imx.git;protocol=https;nobranch=1"
IMX_LIBDRM_BRANCH ?= "libdrm-imx-2.4.102"
SRC_URI = "${IMX_LIBDRM_SRC};branch=${IMX_LIBDRM_BRANCH} \
           file://0001-meson-add-libdrm-vivante-to-the-meson-meta-data.patch "
SRCREV = "f525ae649cd6e81e5d4e459799b0f7a120c4e174"
S = "${WORKDIR}/git"

DEFAULT_PREFERENCE = "-1"
COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"

inherit meson pkgconfig manpages

PACKAGECONFIG ??= "libkms intel radeon amdgpu nouveau vmwgfx omap freedreno vc4 etnaviv install-test-programs"
PACKAGECONFIG[libkms] = "-Dlibkms=true,-Dlibkms=false"
PACKAGECONFIG[intel] = "-Dintel=true,-Dintel=false,libpciaccess"
PACKAGECONFIG[radeon] = "-Dradeon=true,-Dradeon=false"
PACKAGECONFIG[amdgpu] = "-Damdgpu=true,-Damdgpu=false"
PACKAGECONFIG[nouveau] = "-Dnouveau=true,-Dnouveau=false"
PACKAGECONFIG[vmwgfx] = "-Dvmwgfx=true,-Dvmwgfx=false"
PACKAGECONFIG[omap] = "-Domap=true,-Domap=false"
PACKAGECONFIG[exynos] = "-Dexynos=true,-Dexynos=false"
PACKAGECONFIG[freedreno] = "-Dfreedreno=true,-Dfreedreno=false"
PACKAGECONFIG[tegra] = "-Dtegra=true,-Dtegra=false"
PACKAGECONFIG[vc4] = "-Dvc4=true,-Dvc4=false"
PACKAGECONFIG[etnaviv] = "-Detnaviv=true,-Detnaviv=false"
PACKAGECONFIG[freedreno-kgsl] = "-Dfreedreno-kgsl=true,-Dfreedreno-kgsl=false"
PACKAGECONFIG[valgrind] = "-Dvalgrind=true,-Dvalgrind=false,valgrind"
PACKAGECONFIG[install-test-programs] = "-Dinstall-test-programs=true,-Dinstall-test-programs=false"
PACKAGECONFIG[cairo-tests] = "-Dcairo-tests=true,-Dcairo-tests=false"
PACKAGECONFIG[udev] = "-Dudev=true,-Dudev=false,udev"
PACKAGECONFIG[manpages] = "-Dman-pages=true,-Dman-pages=false,libxslt-native xmlto-native"

ALLOW_EMPTY_${PN}-drivers = "1"
PACKAGES =+ "${PN}-tests ${PN}-drivers ${PN}-radeon ${PN}-nouveau ${PN}-omap \
             ${PN}-intel ${PN}-exynos ${PN}-kms ${PN}-freedreno ${PN}-amdgpu \
             ${PN}-etnaviv"

RRECOMMENDS_${PN}-drivers = "${PN}-radeon ${PN}-nouveau ${PN}-omap ${PN}-intel \
                             ${PN}-exynos ${PN}-freedreno ${PN}-amdgpu \
                             ${PN}-etnaviv"

FILES_${PN}-tests = "${bindir}/*"
FILES_${PN}-radeon = "${libdir}/libdrm_radeon.so.*"
FILES_${PN}-nouveau = "${libdir}/libdrm_nouveau.so.*"
FILES_${PN}-omap = "${libdir}/libdrm_omap.so.*"
FILES_${PN}-intel = "${libdir}/libdrm_intel.so.*"
FILES_${PN}-exynos = "${libdir}/libdrm_exynos.so.*"
FILES_${PN}-kms = "${libdir}/libkms*.so.*"
FILES_${PN}-freedreno = "${libdir}/libdrm_freedreno.so.*"
FILES_${PN}-amdgpu = "${libdir}/libdrm_amdgpu.so.* ${datadir}/${PN}/amdgpu.ids"
FILES_${PN}-etnaviv = "${libdir}/libdrm_etnaviv.so.*"

BBCLASSEXTEND = "native nativesdk"

PACKAGES_prepend_imxgpu = "${PN}-vivante "
RRECOMMENDS_${PN}-drivers_append_imxgpu = " ${PN}-vivante"
FILES_${PN}-vivante = "${libdir}/libdrm_vivante.so.*"
PACKAGECONFIG_append_imxgpu = " vivante"
PACKAGECONFIG[vivante] = "-Dvivante=true,-Dvivante=false"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
