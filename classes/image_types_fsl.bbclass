inherit image_types

# Handle u-boot suffixes
UBOOT_SUFFIX ?= "bin"

#
# Handles i.MX mxs bootstream generation
#
MXSBOOT_NAND_ARGS ?= ""

# U-Boot mxsboot generation for uSD
do_image_uboot_mxsboot_sdcard[depends] += "u-boot-mxsboot-native:do_populate_sysroot \
                                           u-boot:do_deploy"
IMAGE_CMD:uboot-mxsboot-sdcard() {
    mxsboot sd ${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE}.${UBOOT_SUFFIX} \
               ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.uboot-mxsboot-sdcard
    ln -sf ${IMAGE_NAME}.rootfs.uboot-mxsboot-sdcard \
           ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.rootfs.uboot-mxsboot-sdcard
}

# U-Boot mxsboot generation for NAND
do_image_uboot_mxsboot_nand[depends] += "u-boot-mxsboot-native:do_populate_sysroot \
                                         u-boot:do_deploy"
IMAGE_CMD:uboot-mxsboot-nand() {
    mxsboot ${MXSBOOT_NAND_ARGS} nand \
            ${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE}.${UBOOT_SUFFIX} \
            ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.uboot-mxsboot-nand
    ln -sf ${IMAGE_NAME}.rootfs.uboot-mxsboot-nand \
           ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.rootfs.uboot-mxsboot-nand
}

# In case we are building for i.MX23 or i.MX28 we need to have the
# image stream built before the wic generation
do_image_wic[depends] += " \
    ${@bb.utils.contains('IMAGE_FSTYPES', 'uboot-mxsboot-sdcard', \
                                          '${IMAGE_BASENAME}:do_image_uboot_mxsboot_sdcard', '', d)} \
"

# We need to apply a fixup inside of the partition table
IMAGE_CMD:wic:append:mxs-generic-bsp() {
	# Change partition type for mxs processor family
	bbnote "Setting partition type to 0x53 as required for mxs' SoC family."
	echo -n S | dd of=$out${IMAGE_NAME_SUFFIX}.wic bs=1 count=1 seek=450 conv=notrunc
}
