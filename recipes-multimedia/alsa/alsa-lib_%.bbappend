FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

IMX_PATCH = " file://0001-add-conf-for-multichannel-support-in-imx.patch \
              file://0004-pcm-Don-t-store-the-state-for-SND_PCM_STATE_SUSPENDE.patch"

SRC_URI_append_mx6 = "${IMX_PATCH}"
SRC_URI_append_mx7 = "${IMX_PATCH}"

PACKAGE_ARCH_mx6 = "${MACHINE_SOCARCH}"
PACKAGE_ARCH_mx7 = "${MACHINE_SOCARCH}"
