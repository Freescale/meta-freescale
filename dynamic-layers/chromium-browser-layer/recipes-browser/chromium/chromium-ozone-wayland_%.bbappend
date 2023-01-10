FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:imx-nxp-bsp = " \
    file://0001-Fixed-chromium-flicker-with-g2d-renderer.patch \
    file://0002-chromium-met-EGL-API-GetProcAddress-failures.patch \
    file://0003-Disable-dri-for-imx-gpu.patch \
"
SRC_URI:append:mx8-nxp-bsp = " \
    file://0101-V4L2VDA-Switch-to-use-VDA-instead-of-direct-VideoDec.patch \
    file://0102-GenericV4L2Device-Correct-v4l2-decoder-device-path.patch \
    file://0103-V4L2VDA-Add-macro-use_linux_v4l2.patch \
    file://0104-V4L2VDA-Create-single-multi-plane-queues.patch \
    file://0105-V4L2Buffer-Allocate-correct-v4l2-buffers-for-queues.patch \
    file://0106-V4L2VDA-Create-videoframe-according-to-v4l2buffer.patch \
    file://0107-V4L2VDA-Add-function-IsMultiQueue-for-S_FMT-and-G_FM.patch \
    file://0108-V4L2VDA-Use-correct-size-to-allocate-CAPTURE-buffer.patch \
    file://0109-V4L2VDA-Use-correct-plane-size-and-bytesused.patch \
    file://0110-V4L2VDA-Add-hevc-format-support.patch \
    file://0111-V4L2VDA-fix-vp9-crash-caused-by-DequeueResolutionCha.patch \
    file://0112-V4L2VDA-Add-fps-in-SkiaOutputSurfaceImplOnGpu-by-VLO.patch \
    file://0113-V4L2VDA-Comment-some-unused-ioctl.patch \
    file://0114-V4L2VDA-Set-OUTPUT-format-with-parsed-resolution-for.patch \
    file://0115-V4L2VDA-Add-V4L2_PIX_FMT_NV12M_8L128-format-for-amph.patch \
    file://0116-V4L2VDA-Support-tile-to-linear-transform-for-amphion.patch \
    file://0117-V4L2VDA-Enlarge-input-buffer-count-to-16.patch \
    file://0118-V4L2VDA-Use-dlopen-to-dynamically-use-g2d-api.patch \
    file://0119-V4L2VDA-dlopen-libg2d.so.2-to-avoid-segfault.patch \
"

GN_ARGS_DISABLE_GBM             = ""
GN_ARGS_DISABLE_GBM:mx6-nxp-bsp = "use_system_minigbm=false use_wayland_gbm=false"
GN_ARGS_DISABLE_GBM:mx7-nxp-bsp = "${GN_ARGS_DISABLE_GBM:mx6-nxp-bsp}"
GN_ARGS_USE_IMXGPU        = "use_imxgpu=false"
GN_ARGS_USE_IMXGPU:imxgpu = "use_imxgpu=true"
GN_ARGS_ENABLE_PROPRIETARY_CODECS             = ""
GN_ARGS_ENABLE_PROPRIETARY_CODECS:mx8-nxp-bsp = "proprietary_codecs=true"
GN_ARGS_FFMPEG_BRANDING             = ""
GN_ARGS_FFMPEG_BRANDING:mx8-nxp-bsp = "ffmpeg_branding="Chrome""
GN_ARGS_USE_V4L2_CODEC             = ""
GN_ARGS_USE_V4L2_CODEC:mx8-nxp-bsp = "use_v4l2_codec=true"
GN_ARGS_USE_LINUX_V4L2_ONLY             = ""
GN_ARGS_USE_LINUX_V4L2_ONLY:mx8-nxp-bsp = "use_linux_v4l2_only=true"
GN_ARGS:append:imx-nxp-bsp = " \
    ${GN_ARGS_DISABLE_GBM} \
    ${GN_ARGS_USE_IMXGPU} \
    ${GN_ARGS_ENABLE_PROPRIETARY_CODECS} \
    ${GN_ARGS_FFMPEG_BRANDING} \
    ${GN_ARGS_USE_V4L2_CODEC} \
    ${GN_ARGS_USE_LINUX_V4L2_ONLY} \
"
CHROMIUM_EXTRA_ARGS:append = " --disable-features=VizDisplayCompositor --in-process-gpu --disable-gpu-rasterization"
