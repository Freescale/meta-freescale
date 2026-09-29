SUMMARY = "i.MX GPU SDK Samples"
DESCRIPTION = "Set of sample applications for i.MX GPU"
HOMEPAGE = "https://github.com/nxp-imx/gtec-demo-framework"
SECTION = "graphics"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://License.md;md5=9d58a2573275ce8c35d79576835dbeb8"

DEPENDS = "\
    assimp \
    cmake-native \
    devil \
    fmt \
    gli \
    glm \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gtest \
    half \
    ninja-native \
    nlohmann-json \
    rapidjson \
    stb \
    zlib \
"

require imx-gpu-sdk-src.inc

PACKAGECONFIG ??= "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', \
       bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', '', d), d)} \
    ${PACKAGECONFIG_G2D} \
    ${PACKAGECONFIG_GLES} \
    ${PACKAGECONFIG_GPUTYPE} \
    ${PACKAGECONFIG_OPENVG} \
    ${PACKAGECONFIG_SOC} \
"

PACKAGECONFIG_G2D = ""
PACKAGECONFIG_G2D:imxgpu2d = "g2d"

PACKAGECONFIG_GLES = ""
PACKAGECONFIG_GLES:imxgpu3d = "gles2"
PACKAGECONFIG_GLES:imxgpu3d:imxmali = "gles2 gles3 gles32"
PACKAGECONFIG_GLES:mx6q-nxp-bsp = "gles2 gles3"
PACKAGECONFIG_GLES:mx6dl-nxp-bsp = "gles2 gles3"
PACKAGECONFIG_GLES:mx8-nxp-bsp = "gles2 gles32"
PACKAGECONFIG_GLES:mx8mm-nxp-bsp = "gles2"

PACKAGECONFIG_GPUTYPE = ""
PACKAGECONFIG_GPUTYPE:imxmali = "mali"
PACKAGECONFIG_GPUTYPE:imxviv = "vivante"

# OpenVG is only implemented by the Vivante GPU driver
PACKAGECONFIG_OPENVG = ""
PACKAGECONFIG_OPENVG:imxviv = "openvg"

# The Vivante driver always provides OpenCL, while other GPUs rely on the
# standard ICD loader, which, like the Vulkan loader, needs the distro feature.
PACKAGECONFIG_SOC = ""
PACKAGECONFIG_SOC:imxmali = "opencv ${@bb.utils.filter('DISTRO_FEATURES', 'opencl vulkan', d)}"
PACKAGECONFIG_SOC:mx8-nxp-bsp = "opencl opencv openvx ${@bb.utils.filter('DISTRO_FEATURES', 'vulkan', d)}"
PACKAGECONFIG_SOC:mx8mm-nxp-bsp = "opencv"

PACKAGECONFIG[g2d] = "G2D,,virtual/libg2d"
PACKAGECONFIG[gles2] = "OpenGLES2,,virtual/libgles2"
PACKAGECONFIG[gles3] = "OpenGLES3,,virtual/libgles3"
PACKAGECONFIG[gles32] = "OpenGLES3.2,,virtual/libgles3"
PACKAGECONFIG[mali] = ""
# rapidopencl, rapidopenvx and rapidvulkan are header-only, so they are added
# to RDEPENDS for them to be included in the SDK
PACKAGECONFIG[opencl] = "OpenCL1.2,,rapidopencl virtual/libopencl1,rapidopencl"
PACKAGECONFIG[opencv] = "OpenCV4,,opencv"
PACKAGECONFIG[openvg] = "OpenVG,,virtual/libopenvg"
PACKAGECONFIG[openvx] = "OpenVX1.2,,rapidopenvx,rapidopenvx"
PACKAGECONFIG[vivante] = "HW_GPU_VIVANTE"
# vulkan-loader is dynamically loaded, so it needs an explicit RDEPENDS
PACKAGECONFIG[vulkan] = "Vulkan1.2,,glslang-native rapidvulkan vulkan-headers vulkan-loader,rapidvulkan vulkan-loader vulkan-validation-layers"
PACKAGECONFIG[wayland] = ",,libxdg-shell wayland,libxdg-shell,,x11"
PACKAGECONFIG[x11] = ",,xrandr,,,wayland"

WINDOW_SYSTEM = "${@bb.utils.contains('PACKAGECONFIG', 'wayland', 'Wayland_XDG', \
                    bb.utils.contains('PACKAGECONFIG', 'x11', 'X11', 'FB', d), d)}"

# FEATURES is a comma-separated list passed to FslBuild.py as
# --UseFeatures [${FEATURES}], built from the fixed features below and the ones
# enabled through PACKAGECONFIG.
PACKAGECONFIG_CONFARGS = "\
    ConsoleHost \
    EarlyAccess \
    EGL \
    GoogleUnitTest \
    Lib_NlohmannJson \
    Test_RequireUserInputToExit \
    WindowHost \
"
FEATURES = "${@','.join(d.getVar('PACKAGECONFIG_CONFARGS').split())}"

EXTENSIONS = "*"
EXTENSIONS:mx6q-nxp-bsp = "OpenGLES:GL_VIV_direct_texture,OpenGLES3:GL_EXT_geometry_shader,OpenGLES3:GL_EXT_tessellation_shader"
EXTENSIONS:mx6dl-nxp-bsp = "OpenGLES:GL_VIV_direct_texture,OpenGLES3:GL_EXT_geometry_shader,OpenGLES3:GL_EXT_tessellation_shader"
EXTENSIONS:mx8m-nxp-bsp = "OpenGLES:GL_VIV_direct_texture,OpenGLES3:GL_EXT_color_buffer_float"
EXTENSIONS:mx8mm-nxp-bsp = "*"
EXTENSIONS:imxmali = "OpenGLES3:GL_EXT_color_buffer_float,OpenGLES3:GL_EXT_geometry_shader,OpenGLES3:GL_EXT_tessellation_shader"

do_compile () {
    export FSL_PLATFORM_NAME=Yocto
    export ROOTFS=${STAGING_DIR_HOST}
    . ./prepare.sh
    FslBuild.py -vvvvv -t sdk --UseFeatures [${FEATURES}] --UseExtensions [${EXTENSIONS}] --Variants [WindowSystem=${WINDOW_SYSTEM}] --BuildThreads ${@oe.utils.parallel_make(d)} -c install --CMakeInstallPrefix ${S}
}

REMOVALS = "\
    GLES2/DeBayer \
    GLES2/DirectMultiSamplingVideoYUV \
    GLES3/DirectMultiSamplingVideoYUV \
"
REMOVALS:append:imxdpu = " \
    G2D/EightLayers \
"
REMOVALS:append:mx6q-nxp-bsp = " \
    GLES3/HDR02_FBBasicToneMapping \
    GLES3/HDR03_SkyboxTonemapping \
    GLES3/HDR04_HDRFramebuffer \
"
REMOVALS:append:mx6dl-nxp-bsp = " \
    GLES3/HDR02_FBBasicToneMapping \
    GLES3/HDR03_SkyboxTonemapping \
    GLES3/HDR04_HDRFramebuffer \
"

do_install () {
    install -d "${D}/opt/${PN}"
    cp -r ${S}/bin/* ${D}/opt/${PN}
    for removal in ${REMOVALS}; do
        rm -rf ${D}/opt/${PN}/$removal
    done
}

FILES:${PN} += "/opt/${PN}"
FILES:${PN}-dbg += "/opt/${PN}/*/*/.debug"
# The SDK demo binaries under /opt are shipped already stripped and carry vendor
# rpaths, so the already-stripped and rpaths QA checks do not apply.
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN} += "already-stripped rpaths"

# Unfortunately recipes with an empty main package, like header-only libraries,
# are not included in the SDK. Use RDEPENDS as a workaround.
RDEPENDS:${PN} += "\
    fmt \
    gli \
    glm \
    googletest \
    half \
    nlohmann-json \
    rapidjson \
    stb \
"

# For backwards compatibility
RPROVIDES:${PN} = "fsl-gpu-sdk"
RREPLACES:${PN} = "fsl-gpu-sdk"
RCONFLICTS:${PN} = "fsl-gpu-sdk"

COMPATIBLE_MACHINE = "(imxgpu)"
