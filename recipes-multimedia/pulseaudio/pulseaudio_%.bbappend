
CACHED_CONFIGUREVARS_append_mx6 = " ax_cv_PTHREAD_PRIO_INHERIT=no"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/imx:"

SRC_URI_append_mx6 = " file://daemon.conf file://default.pa"
SRC_URI_append_mx6ul = " file://daemon.conf file://default.pa"
SRC_URI_append_mx7 = " file://daemon.conf file://default.pa"

do_install_append() {
    if [ -e "${WORKDIR}/daemon.conf" ] && [ -e "${WORKDIR}/default.pa" ]; then
        install -m 0644 ${WORKDIR}/daemon.conf ${D}${sysconfdir}/pulse/daemon.conf
        install -m 0644 ${WORKDIR}/default.pa ${D}${sysconfdir}/pulse/default.pa
    fi
}

PACKAGE_ARCH_mx6 = "${MACHINE_SOCARCH}"
PACKAGE_ARCH_mx6ul = "${MACHINE_SOCARCH}"
PACKAGE_ARCH_mx7 = "${MACHINE_SOCARCH}"
