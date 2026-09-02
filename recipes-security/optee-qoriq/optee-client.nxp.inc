# Copyright 2020-2021,2025 NXP

require recipes-security/optee-imx/optee-client-fslc.inc

# The patch same as imx-optee, so point FILESEXTRAPATHS to optee-imx/optee-client,
# avoid duplicate copy files to optee-qoriq
# The entry cannot be dropped: this recipe's BPN is optee-client-qoriq, so the
# default FILESPATH only searches directories named after it, and the shared
# i.MX directory that holds tee-supplicant.sh is unreachable without this line.
# The entry also keeps the files this include adds to SRC_URI reachable when a
# recipe in another layer requires the include.
# nooelint: oelint.vars.outofcontext
FILESEXTRAPATHS:prepend := "${THISDIR}/../optee-imx/optee-client:"

DEPENDS = "util-linux-libuuid"

SRC_URI:remove = "git://github.com/OP-TEE/optee_client.git;branch=master;protocol=https"
SRC_URI:prepend = "${OPTEE_CLIENT_SRC};branch=${OPTEE_CLIENT_BRANCH} "

OPTEE_CLIENT_SRC ?= "git://github.com/nxp-qoriq/optee_client.git;protocol=https"

inherit pkgconfig

do_install:append () {
    if ! ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        rm -rf ${D}${libdir}/systemd
    fi
}
