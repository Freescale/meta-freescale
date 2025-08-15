DESCRIPTION = "Vulkan Window System Integration Layer"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c2e771b72d60a13d2de384cb49055d00"
DEPENDS = "libdrm vulkan-loader"

PV = "0.0+git${SRCPV}"

SRC_URI = "git://gitlab.freedesktop.org/mesa/vulkan-wsi-layer.git;protocol=https;branch=main \
           file://0001-MGS-6801-ccc-vkmark-on-wayland.patch \
           file://0002-MGS-6823-nxp-Add-support-of-VK_COMPOSITE_ALPHA_OPAQU.patch \
           file://0003-Update-minimum-version-of-CMake.patch"
SRCREV = "cb1a50cf7e640ad7306e673131ded98c0f133628"

inherit cmake pkgconfig

PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', 'headless', d)}"

PACKAGECONFIG[headless] = " \
    -DBUILD_WSI_HEADLESS=1, \
    -DBUILD_WSI_HEADLESS=0, \
    ,,, \
    wayland"
PACKAGECONFIG[wayland] = " \
    -DBUILD_WSI_WAYLAND=1 -DENABLE_WAYLAND_FIFO_PRESENTATION_THREAD=1 -DSELECT_EXTERNAL_ALLOCATOR=dma_buf_heaps, \
    -DBUILD_WSI_WAYLAND=0, \
    wayland wayland-native wayland-protocols,,, \
    headless"

EXTRA_OECMAKE = " \
    -DBUILD_WSI_DISPLAY=0 \
    -DBUILD_WSI_IMAGE_COMPRESSION_CONTROL_SWAPCHAIN=1 \
    -DCMAKE_BUILD_TYPE=Release \
    -DCMAKE_EXPORT_COMPILE_COMMANDS=1 \
    -DENABLE_INSTRUMENTATION=1 \
    -DKERNEL_HEADER_DIR=${KERNEL_HEADER_DIR} \
    -DVULKAN_WSI_LAYER_EXPERIMENTAL=0 "

# The KERNEL_HEADER_DIR setting is required by the CMake apparently
# in order to find the DRM headers. However, the Yocto build provides
# the DRM headers via a separate recipe libdrm in order to avoid the
# kernel dependency. The CMake fails if the variable is not defined,
# so set it to an invalid value in case the build ever actually needs
# the kernel headers for something else.
KERNEL_HEADER_DIR = "KERNEL_HEADER_DIR_NOT_PROVIDED_BY_YOCTO"

do_install() {
    install -d ${D}${sysconfdir}/vulkan/implicit_layer.d
    install -m 0755 ${B}/libVkLayer_window_system_integration.so ${D}${sysconfdir}/vulkan/implicit_layer.d/
    install -m 0644 ${B}/VkLayer_window_system_integration.json ${D}${sysconfdir}/vulkan/implicit_layer.d
}

# Adjust packaging variables for unversioned library
SOLIBS = ".so"
FILES_SOLIBSDEV = ""
