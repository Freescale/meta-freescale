FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

IMX_PATCH = " \
    file://0001-add-conf-for-multichannel-support-in-imx.patch \
    file://0004-pcm-Don-t-store-the-state-for-SND_PCM_STATE_SUSPENDE.patch \
    file://0005-add-ak4458-conf-for-multichannel-support.patch \
    file://0006-add-conf-for-iMX-XCVR-sound-card.patch \
"
SRC_URI_append_imx = "${IMX_PATCH}"

PACKAGE_ARCH_imx = "${MACHINE_SOCARCH}"
