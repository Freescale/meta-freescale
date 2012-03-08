PR .= "+${DISTRO}.0"

FILESEXTRAPATHS_prepend := "${THISDIR}/busybox-1.18.5:"

SRC_URI += "file://0001-libbb.h-do-not-use-homegrown-struct-sysinfo.patch \
	file://0002-work-around-sysinfo.h-versus-linux-.h-problems.patch \
        file://inetd \
        file://inetd.conf \
        file://defconfig-maximum \
"

do_configure_prepend () {
        cp ${WORKDIR}/defconfig-maximum ${WORKDIR}/defconfig
}

