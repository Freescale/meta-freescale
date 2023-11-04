FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}/imx-nxp-bsp:"

SRC_URI += "${SRC_URI_IMX}"
SRC_URI_IMX             = ""
SRC_URI_IMX:mx6-nxp-bsp = " \
    file://0001-Fix-pulseaudio-mutex-issue-when-do-pause-in-gstreame.patch \
    file://daemon.conf \
    file://default.pa"
SRC_URI_IMX:mx7-nxp-bsp = " \
    ${SRC_URI_IMX:mx6-nxp-bsp} \
    file://0100-pulseaudio-remove-the-control-for-speaker-headphone-widge.patch"
SRC_URI_IMX:mx8-nxp-bsp = " \
    ${SRC_URI_IMX:mx6-nxp-bsp}"
SRC_URI_IMX:mx9-nxp-bsp = " \
    ${SRC_URI_IMX:mx6-nxp-bsp}"

CACHED_CONFIGUREVARS:append:mx6-nxp-bsp = " ax_cv_PTHREAD_PRIO_INHERIT=no"

do_install:append() {
    if [ -e "${WORKDIR}/daemon.conf" ] && [ -e "${WORKDIR}/default.pa" ]; then
        install -m 0644 ${WORKDIR}/daemon.conf ${D}${sysconfdir}/pulse/daemon.conf
        install -m 0644 ${WORKDIR}/default.pa ${D}${sysconfdir}/pulse/default.pa
    fi
}

PACKAGE_ARCH:mx6-nxp-bsp = "${MACHINE_SOCARCH}"
PACKAGE_ARCH:mx7-nxp-bsp = "${MACHINE_SOCARCH}"
PACKAGE_ARCH:mx8-nxp-bsp = "${MACHINE_SOCARCH}"
PACKAGE_ARCH:mx9-nxp-bsp = "${MACHINE_SOCARCH}"
