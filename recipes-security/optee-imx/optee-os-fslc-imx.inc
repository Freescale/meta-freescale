# Copied from meta-imx/meta-imx-bsp/recipes-security/optee/optee-os-imx.inc.
# See: https://github.com/nxp-imx/imx-manifest/blob/imx-linux-walnascar/imx-6.12.20-2.0.0.xml#L37

require optee-os-common-fslc-imx.inc

do_compile:arm() {
    oe_runmake -C ${S} all uTee
}

do_install:append () {
    # Install embedded TAs
    install -d ${D}${nonarch_base_libdir}/optee_armtz/
    install -m 444 ${B}/ta/*/*.ta ${D}${nonarch_base_libdir}/optee_armtz/
}

do_deploy:append() {
    cp ${B}/core/tee-raw.bin ${DEPLOYDIR}/${MLPREFIX}optee/tee.${PLATFORM_FLAVOR}.bin
    ln -sf ${MLPREFIX}optee/tee.${PLATFORM_FLAVOR}.bin ${DEPLOYDIR}/tee.bin
}

do_deploy:append:arm() {
    cp ${B}/core/uTee ${DEPLOYDIR}/${MLPREFIX}optee/uTee-${OPTEE_BIN_EXT}
    ln -sf ${MLPREFIX}optee/uTee-${OPTEE_BIN_EXT} ${DEPLOYDIR}/uTee-${OPTEE_BIN_EXT}
}

FILES:${PN} += "${nonarch_base_libdir}/optee_armtz"
