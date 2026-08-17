require optee-os.nxp.inc

SUMMARY = "OP-TEE Trusted OS TA devkit"
DESCRIPTION = "OP-TEE TA devkit for build TAs"
HOMEPAGE = "https://www.op-tee.org/"

DEPENDS += "python3-pycryptodome-native"

OPTEE_OS_BRANCH = "lf-6.12.49_2.2.0"
SRCREV = "b3883a773a9d15ec6439f9229e48f540c37e0d00"

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
# default from optee-os-fslc.inc (the devkit has no firmware payload). The
# include's FILES:${PN} overrides the bitbake.conf default with a hard "=", so
# a "?=" here would let that config default win instead; and the devkit content
# is not additive to the base package. Keep the full replacement.
# nooelint: oelint.var.filesoverride oelint.var.override
FILES:${PN} = "${includedir}/optee/"
