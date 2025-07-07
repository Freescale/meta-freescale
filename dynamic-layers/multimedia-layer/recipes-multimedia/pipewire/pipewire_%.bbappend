FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:imx-nxp-bsp = " file://0001-launch-allow-pipewire-pulse-can-be-started-by-root.patch"

SYSTEMD_AUTO_ENABLE:imx-nxp-bsp = "disable"

DEPENDS:append:mx95-nxp-bsp = " libdrm"

PACKAGECONFIG:remove = "${PACKAGECONFIG_REMOVE}"
PACKAGECONFIG_REMOVE              ?= ""
PACKAGECONFIG_REMOVE:mx6-nxp-bsp  ?= "gstreamer"
PACKAGECONFIG_REMOVE:mx7-nxp-bsp  ?= "gstreamer"
PACKAGECONFIG_REMOVE:mx8-nxp-bsp  ?= "gstreamer"
PACKAGECONFIG_REMOVE:mx93-nxp-bsp ?= "gstreamer"

PACKAGECONFIG:class-target:append:imx-nxp-bsp = " ${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', 'bluez-lc3', '', d)}"

# FIXME: Needs to qualify on PACKAGECONFIG
SYSTEMD_SERVICE:${PN}-pulse = "pipewire-pulse.service"
