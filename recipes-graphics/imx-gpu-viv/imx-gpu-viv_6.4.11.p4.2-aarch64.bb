require imx-gpu-viv-6-overrides.inc

# 1. Explicitly define the license
LICENSE = "Proprietary"

# 2. Add EULA to the source list
FILESEXTRAPATHS:prepend := "${THISDIR}/../..:"
SRC_URI += "file://EULA"

# 3. Define checksums
LIC_FILES_CHKSUM = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"

IMX_SRCREV_ABBREV = "846e12e"
SRC_URI[sha256sum] = "aa120f37d9010fbddabc60387300fabaf43b2cf30894b6e7b5973947ec6bbe62"
COMPATIBLE_MACHINE = "(mx8-nxp-bsp)"

# 4. FIX: Update the path to look inside the 'sources' folder
copy_eula_to_source() {
    # Try the standard WORKDIR location first
    if [ -f "${UNPACKDIR}/EULA" ]; then
        cp "${UNPACKDIR}/EULA" "${S}/EULA"
    else
        bberror "Could not find EULA in ${UNPACKDIR} file to copy!"
    fi
}

do_unpack[postfuncs] += "copy_eula_to_source"
