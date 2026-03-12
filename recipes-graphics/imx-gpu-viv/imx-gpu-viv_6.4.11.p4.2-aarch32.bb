require imx-gpu-viv-6-overrides.inc

# 1. Explicitly define the license
LICENSE = "Proprietary"

# 2. Add EULA to the source list
FILESEXTRAPATHS:prepend := "${THISDIR}/../..:"
SRC_URI += "file://EULA"

# 3. Define checksums (Same COPYING and EULA md5s as the 64-bit version)
LIC_FILES_CHKSUM = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"

IMX_SRCREV_ABBREV = "846e12e"
SRC_URI[sha256sum] = "19a6377c817e0049f8b6bddb78badc18adccb2075429c27c89aee95d44196113"
COMPATIBLE_MACHINE = "(mx6q-nxp-bsp|mx6dl-nxp-bsp|mx6sx-nxp-bsp|mx6sl-nxp-bsp|mx7ulp-nxp-bsp)"

# 4. FIX: The post-function to handle the EULA location
copy_eula_to_source() {
    # Try the standard WORKDIR location first
    if [ -f "${UNPACKDIR}/EULA" ]; then
        cp "${UNPACKDIR}/EULA" "${S}/EULA"
    else
        bberror "Could not find EULA in ${UNPACKDIR} file to copy!"
    fi
}

do_unpack[postfuncs] += "copy_eula_to_source"
