require conf/machine/include/imx8dxl-evk.inc

KERNEL_DEVICETREE_BASENAME = "imx8dxl-evk"

KERNEL_DEVICETREE:append:use-nxp-bsp = " \
    freescale/${KERNEL_DEVICETREE_BASENAME}-enet0.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-enet0-tja1100.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-lcdif.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-lpspi-slave.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-pcie-ep.dtb \
"

UBOOT_CONFIG_BASENAME = "imx8dxl_evk"

UBOOT_CONFIG[lcd] = "${UBOOT_CONFIG_BASENAME}_lcd_defconfig"

BOARD_TYPE = "evk"
