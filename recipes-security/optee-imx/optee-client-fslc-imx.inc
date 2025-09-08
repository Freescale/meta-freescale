# Copied from meta-imx/meta-imx-bsp/recipes-security/optee/optee-client-imx.inc.
# See: https://github.com/nxp-imx/imx-manifest/blob/imx-linux-scarthgap/imx-6.6.52-2.2.0.xml#L37

require optee-client-fslc.inc

DEPENDS += "util-linux-libuuid"

FILESEXTRAPATHS:prepend := "${THISDIR}/optee-client:"
SRC_URI:remove = "git://github.com/OP-TEE/optee_client.git;branch=master;protocol=https"
SRC_URI:prepend = "${OPTEE_CLIENT_SRC};branch=${SRCBRANCH} "
OPTEE_CLIENT_SRC ?= "git://github.com/nxp-imx/imx-optee-client.git;protocol=https"

inherit pkgconfig

EXTRA_OECMAKE += "-DCFG_TEE_CLIENT_LOAD_PATH=${nonarch_base_libdir}"

# Copy the udev rule from the libts recipe for starting tee-supplicant@.service
SRC_URI += "file://tee-udev.rules"
# Unix group name for dev/tee* ownership.
TEE_GROUP_NAME ?= "teeclnt"
do_install:append () {
    if ${@oe.utils.conditional('VIRTUAL-RUNTIME_dev_manager', 'busybox-mdev', 'false', 'true', d)}; then
        install -d ${D}${nonarch_base_libdir}/udev/rules.d/
        install -m 755 ${WORKDIR}/tee-udev.rules ${D}${nonarch_base_libdir}/udev/rules.d/
        sed -i -e "s/teeclnt/${TEE_GROUP_NAME}/" ${D}${nonarch_base_libdir}/udev/rules.d/tee-udev.rules
    fi

    if [ "${libdir}" != "${nonarch_base_libdir}" ]; then
        rm -rf ${D}${libdir}/systemd
    fi
}
inherit ${@oe.utils.conditional('VIRTUAL-RUNTIME_dev_manager', 'busybox-mdev', '', 'useradd', d)}
USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM:${PN} = "--system ${TEE_GROUP_NAME}"

FILES:${PN} += "${libdir}/* ${includedir}/*"
