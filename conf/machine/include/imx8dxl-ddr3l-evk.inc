require conf/machine/include/imx8dxl-evk.inc

KERNEL_DEVICETREE_BASENAME = "imx8dxl-ddr3l-evk"

UBOOT_CONFIG_BASENAME = "imx8dxl_ddr3l_evk"

UBOOT_CONFIG[nand] = "${UBOOT_CONFIG_BASENAME}_nand_defconfig"

BOARD_TYPE = "ddr3-evk"
