FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

DEPENDS:append:mx95-nxp-bsp = " libdrm"

SRC_URI:append:imx-nxp-bsp = " \
    file://0001-systemd-allow-pipewire-user-services-for-root.patch \
    file://0001-YOCIMX-9095-pipewiresrc-set-rank-to-secondary.patch \
"
SRC_URI:append:mx95-nxp-bsp = " \
    file://0003-pipewiresrc-update-per-plane-stride-and-offset-accor.patch \
"

# Dispatch line consumes the machine-specialized helper var below.
PACKAGECONFIG:remove = "${PACKAGECONFIG_REMOVE}"
PACKAGECONFIG_REMOVE ?= ""
PACKAGECONFIG_REMOVE:mx6-nxp-bsp ?= "gstreamer"
PACKAGECONFIG_REMOVE:mx7-nxp-bsp ?= "gstreamer"

PACKAGECONFIG:append:class-target:imx-nxp-bsp = " ${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', 'bluez-lc3', '', d)}"

# FIXME: Needs to qualify on PACKAGECONFIG
SYSTEMD_SERVICE:${PN}-pulse = "pipewire-pulse.service"
