FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

LIC_FILES_CHKSUM:remove = " \
    file://${S}/base/third_party/xdg_mime/LICENSE;md5=743a91118d40f173467c8ecfd8793c62 \
    file://${S}/third_party/ffmpeg/CREDITS.chromium;md5=59444bcdde2b97bcd19305f2f60e1b54 \
"
LIC_FILES_CHKSUM:append = " \
    file://${S}/base/third_party/xdg_mime/LICENSE;md5=8e8261c342b33e06648483ac7f4862c9 \
    file://${S}/third_party/ffmpeg/CREDITS.chromium;md5=aa2247c58648fadacf4d7f865259eebe \
"

SRC_URI:append:imx-nxp-bsp = " \
    file://0001-Add-knob-for-imx-gpu.patch \
    file://0002-Disable-dri-for-imx-gpu.patch \
    file://0003-Fixed-chromium-flicker-with-g2d-renderer.patch \
    file://0004-chromium-met-EGL-API-GetProcAddress-failures.patch \
"
SRC_URI:append:mx8-nxp-bsp = " \
    file://0101-FFmpeg-enable-vp8-vp9-h264-hevc-v4l2-config.patch \
    file://0102-FFmpeg-change-call-sequence-to-pass-unit-test.patch \
    file://0103-FFmpeg-add-flush-operation-and-correct-timestamp.patch \
    file://0104-FFmpeg-correctly-select-decoder-according-to-soc_id.patch \
    file://0105-Chromium-enable-proprietary-codecs-and-support-HEVC-.patch \
    file://0106-Chromium-get-video-buffer-from-ffmpeg-and-correct-pt.patch \
    file://0107-Chromium-change-stride-alignment-from-32-to-16.patch \
"

GN_ARGS:append:imx-nxp-bsp = " \
    ${GN_ARGS_DISABLE_GBM} \
    ${GN_ARGS_USE_IMXGPU} \
    ${GN_ARGS_ENABLE_PROPRIETARY_CODECS} \
"
GN_ARGS_DISABLE_GBM             = ""
GN_ARGS_DISABLE_GBM:mx6-nxp-bsp = "use_system_minigbm=false use_wayland_gbm=false"
GN_ARGS_DISABLE_GBM:mx7-nxp-bsp = "${GN_ARGS_DISABLE_GBM:mx6-nxp-bsp}"
GN_ARGS_USE_IMXGPU        = "use_imxgpu=false"
GN_ARGS_USE_IMXGPU:imxgpu = "use_imxgpu=true"
GN_ARGS_ENABLE_PROPRIETARY_CODECS             = ""
GN_ARGS_ENABLE_PROPRIETARY_CODECS:mx8-nxp-bsp = "proprietary_codecs=true"

CHROMIUM_EXTRA_ARGS:append:imx-nxp-bsp = " --disable-features=VizDisplayCompositor --in-process-gpu"
