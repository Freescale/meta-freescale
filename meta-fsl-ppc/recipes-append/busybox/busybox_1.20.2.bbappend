PR_append_fsl = "+${DISTRO}.0"

FILESEXTRAPATHS_prepend_fsl := "${THISDIR}/busybox-1.19.4:"

SRC_URI_append_fsl += "file://defconfig-fsl \
            file://busybox-1.19.4-ubi-user-h.patch \
            file://inetd \
            file://inetd.conf \
"

do_configure_prepend_fsl () {
        cp ${WORKDIR}/defconfig-fsl ${WORKDIR}/defconfig
}


