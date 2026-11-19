require mali-imx.inc

LICENSE = "LicenseRef-Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=ef4f968b9dae8ba406390cd617c2301d"

SRC_URI = "${FSL_MIRROR}/${BP}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true \
    file://mali-imx \
    file://mali-imx-boot \
    file://mali-imx.service \
    file://mali-imx.conf \
    file://mali-imx-mesa.conf \
    file://panthor.conf"
SRC_URI[sha256sum] = "112d30ff10d8668c5e721f9f4dcad6e621af62aee74322072aa3923089ad1f1c"
IMX_SRCREV_ABBREV = "18cb429"

S = "${UNPACKDIR}/${BP}-${IMX_SRCREV_ABBREV}"

inherit fsl-eula-unpack
# The flagged trailing space is in the empty expansion of this line, not in
# the file.
# nooelint: oelint.spaces.lineend
inherit_defer ${@oe.utils.conditional('IMX_MALI_DUAL_DRIVER', '1', 'systemd', '', d)}

# IMX_MALI_DEFAULT_DRIVER is mali-imx or mesa; the mali-imx tool calls the
# Mali DDK arm-ddk.
MALI_IMX_DEFAULT_DRIVER = "${@oe.utils.conditional('IMX_MALI_DEFAULT_DRIVER', 'mesa', 'mesa', 'arm-ddk', d)}"

SYSTEMD_PACKAGES = "${PN}-dual"
SYSTEMD_SERVICE:${PN}-dual = "mali-imx.service"

do_install () {
    install -d ${D}
    cp -r ${S}/etc ${S}/usr ${D}

    # Move firmware to nonarch_base_libdir
    if [ "${base_libdir}" != "${nonarch_base_libdir}" ]; then
        install -d ${D}${nonarch_base_libdir}
        mv ${D}${base_libdir}/firmware ${D}${nonarch_base_libdir}
    fi

    if [ "${IMX_MALI_DUAL_DRIVER}" = "1" ]; then
        install -d ${D}${bindir} ${D}${libexecdir} ${D}${systemd_system_unitdir}
        install -m 0755 ${UNPACKDIR}/mali-imx ${D}${bindir}
        install -m 0755 ${UNPACKDIR}/mali-imx-boot ${D}${libexecdir}
        install -m 0644 ${UNPACKDIR}/mali-imx.service ${D}${systemd_system_unitdir}

        # Mesa provides the headers and pkg-config files
        rm -rf ${D}${includedir} ${D}${libdir}/pkgconfig

        # Move the libraries beside Mesa's; mali-imx-boot links the active ones
        install -d ${D}${libdir}/mali-imx
        find ${D}${libdir} -maxdepth 1 \( -name "libmali.so*" -o -name "libEGL.so*" \
            -o -name "libGLESv1_CM.so*" -o -name "libGLESv2.so*" -o -name "libgbm.so*" \) \
            -exec mv {} ${D}${libdir}/mali-imx/ \;

        sed -i 's|"library_path": "${libdir}/libmali.so"|"library_path": "${libdir}/mali-imx/libmali.so"|' \
            ${D}${datadir}/vulkan/icd.d/mali_icd.json
        sed -i 's|^libmali.so$|${libdir}/mali-imx/libmali.so|' \
            ${D}${sysconfdir}/OpenCL/vendors/mali-imx.icd

        # Configure the driver used at first boot. The archive already loads
        # mali_kbase and blacklists panthor, as needed for arm-ddk.
        install -d ${D}${sysconfdir}/mali-imx
        sed -e 's/@MALI_IMX_DEFAULT_DRIVER@/${MALI_IMX_DEFAULT_DRIVER}/' \
            ${UNPACKDIR}/mali-imx.conf > ${D}${sysconfdir}/mali-imx/mali-imx.conf
        if [ "${MALI_IMX_DEFAULT_DRIVER}" = "mesa" ]; then
            install -m 0644 ${UNPACKDIR}/mali-imx-mesa.conf ${D}${sysconfdir}/modprobe.d/mali-imx.conf
            rm ${D}${nonarch_libdir}/modules-load.d/mali_kbase.conf
            install -m 0644 ${UNPACKDIR}/panthor.conf ${D}${nonarch_libdir}/modules-load.d/
        fi
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
