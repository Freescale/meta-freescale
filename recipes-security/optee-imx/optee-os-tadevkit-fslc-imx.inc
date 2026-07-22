# Copied from meta-imx/meta-imx-bsp/recipes-security/optee/optee-os-tadevkit-imx.inc.
# See: https://github.com/nxp-imx/imx-manifest/blob/imx-linux-whinlatter/imx-6.18.2-1.0.0.xml#L39

require optee-os-common-fslc-imx.inc

SUMMARY = "OP-TEE Trusted OS TA devkit"
DESCRIPTION = "OP-TEE TA development kit (headers, linker script and helper makefiles) used to build Trusted Applications."

# single entry
# nooelint: oelint.vars.dependsordered
DEPENDS += "python3-pycryptodome-native"

do_install() {
    #install TA devkit
    install -d ${D}${includedir}/optee/export-user_ta/
    for f in ${B}/export-ta_${OPTEE_ARCH}/* ; do
        cp -R $f ${D}${includedir}/optee/export-user_ta/
    done
}

do_deploy() {
    echo "Do not inherit do_deploy from optee-os."
}

# devkit ships only the export include tree
# nooelint: oelint.var.override oelint.var.filesoverride
FILES:${PN} = "${includedir}/optee/"

# Build paths are currently embedded
# devkit headers embed cross-build paths
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN}-dev += "buildpaths"
