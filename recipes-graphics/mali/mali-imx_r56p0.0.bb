require mali-imx.inc

LICENSE = "LicenseRef-Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=ef4f968b9dae8ba406390cd617c2301d"

SRC_URI = "${FSL_MIRROR}/${BP}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true"
SRC_URI[sha256sum] = "112d30ff10d8668c5e721f9f4dcad6e621af62aee74322072aa3923089ad1f1c"
IMX_SRCREV_ABBREV = "18cb429"

S = "${UNPACKDIR}/${BP}-${IMX_SRCREV_ABBREV}"

inherit fsl-eula-unpack

do_install () {
    install -d ${D}
    cp -r ${S}/etc ${S}/usr ${D}

    # Move firmware to nonarch_base_libdir
    if [ "${base_libdir}" != "${nonarch_base_libdir}" ]; then
        install -d ${D}${nonarch_base_libdir}
        mv ${D}${base_libdir}/firmware ${D}${nonarch_base_libdir}
    fi
}

# The r56p0.0 binaries are linked without a GNU hash section, so the
# ldflags QA check cannot pass for the packages that ship them.
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN} += "ldflags"
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN}-dev += "ldflags"
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN}-libegl += "ldflags"
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN}-libgbm += "ldflags"
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN}-libgles1 += "ldflags"
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN}-libgles2 += "ldflags"
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN}-opencl-icd-dev += "ldflags"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
