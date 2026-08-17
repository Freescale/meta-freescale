# Copied from meta-imx/meta-imx-bsp/recipes-security/optee/optee-os-tadevkit-imx.inc.
# See: https://github.com/nxp-imx/imx-manifest/blob/imx-linux-whinlatter/imx-6.18.2-1.0.0.xml#L39

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

# The TA devkit ships only its headers under ${includedir}/optee, packed
# explicitly. This deliberately replaces the "${nonarch_base_libdir}/firmware/"
# default from optee-os-fslc.inc (the devkit has no firmware payload), so
# oelint.var.override flags the shared value as fully replaced. The include's
# FILES:${PN} itself overrides the bitbake.conf default with a hard "=", so a
# "?=" here would let that config default win instead; and the devkit content
# is not additive to the base package. Keep the full replacement. JUSTIFIED.
# nooelint: oelint.var.filesoverride
# nooelint: oelint.var.override
FILES:${PN} = "${includedir}/optee/"

# Build paths are currently embedded
INSANE_SKIP:${PN}-dev += "buildpaths"
