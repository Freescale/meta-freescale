# Copyright 2020-2024 NXP

SUMMARY = "Basler camera binary drivers"
DESCRIPTION = "Basler camera binary drivers for the i.MX ISP."
HOMEPAGE = "https://www.nxp.com/"
SECTION = "multimedia"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"

IMX_SRCREV_ABBREV = "dd86758"

inherit fsl-eula-unpack

SRC_URI = "${FSL_MIRROR}/${BP}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true"
SRC_URI[sha256sum] = "4c3cfc96833a7d8462f71589152ef7f9c8fcc9badbb925bbc120431f8c5ee3b5"

S = "${UNPACKDIR}/${BP}-${IMX_SRCREV_ABBREV}"

do_compile[noexec] = "1"

do_install() {
    oe_runmake install INSTALL_DIR=${D}
}

SYSTEMD_AUTO_ENABLE = "enable"

# The Basler camera stack installs into ${libdir} and /opt and is packed explicitly.
# nooelint: oelint.var.filesoverride
FILES:${PN} = "${libdir} /opt"
# The Basler camera stack ships prebuilt binaries that are already stripped, so
# the already-stripped QA check does not apply.
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN} = "already-stripped"
RDEPENDS:${PN} += "isp-imx"

COMPATIBLE_MACHINE = "(mx8mp-nxp-bsp)"
