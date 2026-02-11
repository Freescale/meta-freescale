require mali-imx.inc

LICENSE = "Proprietary"

# 1. Point to layer root for common EULA
FILESEXTRAPATHS:prepend := "${THISDIR}/../..:"

# 2. Fetch binary + EULA
SRC_URI = "${FSL_MIRROR}/${BPN}-${PV}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true \
           file://EULA"

# 3. Checksums (BitBake will now look for these inside the S directory)
LIC_FILES_CHKSUM = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"

SRC_URI[sha256sum] = "a11ee45cc191efd7560c0c31b4ee27529ad1767edb21550f217aea2bcd16b11c"
IMX_SRCREV_ABBREV = "1fd73cb"

inherit fsl-eula-unpack

# 4. FIX: Set S to the actual directory created by the binary
S = "${UNPACKDIR}/${BPN}-${PV}-${IMX_SRCREV_ABBREV}"

# 5. FIX: Move EULA from the download root into the Source directory
copy_eula_to_source() {
    # The EULA is downloaded to UNPACKDIR (root), but we need it inside S (subdir)
    if [ -f "${UNPACKDIR}/EULA" ]; then
        cp "${UNPACKDIR}/EULA" "${S}/EULA"
    else
        bberror "Could not find EULA file in ${UNPACKDIR} to copy!"
    fi
}

do_unpack[postfuncs] += "copy_eula_to_source"

do_install:append() {
    if [ "${base_libdir}" != "${nonarch_base_libdir}" ]; then
        install -d ${D}${nonarch_base_libdir}
        mv ${D}${base_libdir}/firmware ${D}${nonarch_base_libdir}
    fi
}

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
