
CACHED_CONFIGUREVARS:append:mx6 = " ax_cv_PTHREAD_PRIO_INHERIT=no"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}/imx:"

SRC_URI:append:mx6 = " file://daemon.conf file://default.pa"
SRC_URI:append:mx7 = " file://daemon.conf file://default.pa \
                       file://pulseaudio-remove-the-control-for-speaker-headphone-widge.patch \
"
SRC_URI:append:mx8 = " file://daemon.conf file://default.pa"

do_install:append() {
    if [ -e "${WORKDIR}/daemon.conf" ] && [ -e "${WORKDIR}/default.pa" ]; then
        install -m 0644 ${WORKDIR}/daemon.conf ${D}${sysconfdir}/pulse/daemon.conf
        install -m 0644 ${WORKDIR}/default.pa ${D}${sysconfdir}/pulse/default.pa
    fi
}

PACKAGE_ARCH:mx6 = "${MACHINE_SOCARCH}"
PACKAGE_ARCH:mx7 = "${MACHINE_SOCARCH}"
PACKAGE_ARCH:mx8 = "${MACHINE_SOCARCH}"
