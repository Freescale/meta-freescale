SUMMARY = "NXP i.MX ELE firmware"
DESCRIPTION = "EdgeLock Secure Enclave firmware for i.MX series SoCs"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://EULA;md5=bc649096ad3928ec06a8713b8d787eac"

inherit fsl-eula-unpack use-imx-security-controller-firmware deploy

# 1. Fetch EULA explicitly
SRC_URI = "${FSL_MIRROR}/${BP}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true \
           file://${FSL_EULA_FILE}"

IMX_SRCREV_ABBREV = "93492e0"
SRC_URI[sha256sum] = "c3ae4a1e1a41da441bd61483ef011ac24bf624aa6bad36f879b5dfa435647b4c"

S = "${UNPACKDIR}/${BP}-${IMX_SRCREV_ABBREV}"

# 2. Hardcode the correct firmware name for i.MX93 to fix "UNDEFINED"
# Usually 'mx93a1-ahab-container.img' for A1 silicon
SECO_FIRMWARE_NAME = "mx93a1-ahab-container.img"

do_compile[noexec] = "1"

# 3. Robust Copy Function (from previous step)
do_copy_eula() {
    EULA_PATH=$(find ${UNPACKDIR} -type f -name EULA | head -n 1)
    if [ -n "$EULA_PATH" ]; then
        cp "$EULA_PATH" "${S}/EULA"
    else
        bbfatal "Could not find EULA file in ${UNPACKDIR}"
    fi
}
do_unpack[postfuncs] += "do_copy_eula"

# 4. Smart Install Function
do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/imx/ele

    # Iterate over the variables
    for fw in ${SECO_FIRMWARE_NAME} ${SECOEXT_FIRMWARE_NAME}; do
        # Skip if the variable is "UNDEFINED" or empty
        if [ "$fw" = "UNDEFINED" ] || [ -z "$fw" ]; then
            continue
        fi

        # Check if the file exists before installing
        if [ -f "${S}/$fw" ]; then
            install -m 0644 "${S}/$fw" "${D}${nonarch_base_libdir}/firmware/imx/ele"
        else
            bbwarn "File '$fw' listed in recipe but not found in source. Skipping."
        fi
    done
}

do_deploy () {
    # Same smart check for deploy
    if [ "${SECO_FIRMWARE_NAME}" != "UNDEFINED" ] && [ -f "${S}/${SECO_FIRMWARE_NAME}" ]; then
        install -m 0644 ${S}/${SECO_FIRMWARE_NAME} ${DEPLOYDIR}
    fi
}
addtask deploy after do_install before do_build

FILES:${PN} = "${nonarch_base_libdir}/firmware"

RREPLACES:${PN} = "firmware-sentinel"
RPROVIDES:${PN} = "firmware-sentinel"

COMPATIBLE_MACHINE = "(mx8ulp-generic-bsp|mx9-generic-bsp)"
