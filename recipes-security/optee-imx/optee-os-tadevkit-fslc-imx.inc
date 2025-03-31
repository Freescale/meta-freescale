# Copied from meta-imx/meta-imx-bsp/recipes-security/optee/optee-os-tadevkit-imx.inc.
# See: https://github.com/nxp-imx/imx-manifest/blob/imx-linux-scarthgap/imx-6.6.52-2.2.0.xml#L37

require optee-os-common-fslc-imx.inc

SUMMARY = "OP-TEE Trusted OS TA devkit"
DESCRIPTION = "OP-TEE TA devkit for build TAs"
HOMEPAGE = "https://www.op-tee.org/"

DEPENDS += "python3-pycryptodome-native"

do_install() {
    #install TA devkit
    install -d ${D}${includedir}/optee/export-user_ta/
    for f in ${B}/export-ta_${OPTEE_ARCH}/* ; do
        cp -aR $f ${D}${includedir}/optee/export-user_ta/
    done
}

do_deploy() {
	echo "Do not inherit do_deploy from optee-os."
}

FILES:${PN} = "${includedir}/optee/"

# Build paths are currently embedded
INSANE_SKIP:${PN}-dev += "buildpaths"
