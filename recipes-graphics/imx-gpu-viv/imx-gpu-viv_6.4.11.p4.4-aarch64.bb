require imx-gpu-viv-6.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"

IMX_SRCREV_ABBREV = "8626797"

SRC_URI[sha256sum] = "8cc97005a7469c0e83139cf1b9a8106fa6f0a84fb6f87d0f6bb9808408305c28"

do_install:prepend() {
    if [ "${IS_MX8}" = "1" ]; then
        if [ ! -z "${PACKAGES_VULKAN}" ]; then
            # Idempotent: this rewrites ${S}, which do_unpack does not restore
            # when do_install re-runs on its own, so skip once already moved.
            if [ -f ${S}/gpu-core/usr/share/vulkan/icd.d/verisilicon_icd.json ]; then
                mkdir -p ${S}/gpu-core/etc/vulkan/icd.d
                mv ${S}/gpu-core/usr/share/vulkan/icd.d/verisilicon_icd.json ${S}/gpu-core/etc/vulkan/icd.d/imx_icd.json
            fi
        fi
    fi
}

do_install:append() {
    if [ "${IS_MX8}" = "1" ]; then
        if [ ! -z "${PACKAGES_VULKAN}" ]; then
            install -d ${D}${datadir}/vulkan/icd.d
            mv ${D}${sysconfdir}/vulkan/icd.d/imx_icd.json ${D}${datadir}/vulkan/icd.d/verisilicon_icd.json
        fi
    fi
}

COMPATIBLE_MACHINE = "(mx8-nxp-bsp)"
