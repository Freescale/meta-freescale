FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

NXP_PATCHES = " \
    file://0001-unstable-Add-alpha-compositing-protocol.patch \
    file://0002-unstable-Add-hdr10-metadata-protocol.patch \
    file://0003-linux-dmabuf-support-passing-buffer-DTRC-meta-to-com.patch \
"

SRC_URI:append:imx-nxp-bsp = " ${NXP_PATCHES}"

# override the effect of "inherit allarch"
python allarch_package_arch_handler:prepend:imx-nxp-bsp () {
    return
}

PACKAGE_ARCH:imx-nxp-bsp = "${MACHINE_SOCARCH}"
