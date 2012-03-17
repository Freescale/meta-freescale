#
# Create an image that can by written onto a SD card using dd.
#
# External variables needed:
#   ${ROOTFS} - the rootfs image to incorporate
#   ${IMAGE_BOOTLOADER} - bootloader to use {u-boot, barebox}x

inherit image

IMAGE_BOOTLOADER ?= "u-boot"

do_rootfs[depends] += "parted-native:do_populate_sysroot \
                       virtual/kernel:do_deploy \
                       ${IMAGE_BOOTLOADER}:do_deploy"

# Add the fstypes we need
IMAGE_FSTYPES += "sdimg"

# Default to 3.4GiB images
SDIMG_SIZE ?= "3400"

# Addional space for boot partition
BOOT_SPACE ?= "10M"

IMAGE_CMD_sdimg () {
	TMP=${WORKDIR}/tmp
	SDIMG=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.sdimg

	dd if=/dev/zero of=${SDIMG} bs=$(expr 1000 \* 1000) count=${SDIMG_SIZE}

	# Create partition table
	parted -s ${SDIMG} mklabel msdos
	parted -s ${SDIMG} mkpart primary ${BOOT_SPACE} 100%
	parted ${SDIMG} print

	case "${IMAGE_BOOTLOADER}" in
		u-boot)
		dd if=${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE}.bin of=${SDIMG} conv=notrunc seek=1 skip=1 bs=512
		dd if=${DEPLOY_DIR_IMAGE}/uImage-${MACHINE}.bin of=${SDIMG} conv=notrunc seek=1 bs=1M
		;;
		barebox)
		dd if=${DEPLOY_DIR_IMAGE}/barebox-${MACHINE}.bin of=${SDIMG} conv=notrunc seek=1 skip=1 bs=512
		dd if=${DEPLOY_DIR_IMAGE}/bareboxenv-${MACHINE}.bin of=${SDIMG} conv=notrunc seek=1 bs=512k
		;;
		*)
		bberror "Unkown IMAGE_BOOTLOADER value"
		exit 1
		;;
	esac

	dd if=${ROOTFS} of=${SDIMG} conv=notrunc seek=1 bs=${BOOT_SPACE}

	cd ${DEPLOY_DIR_IMAGE}
	ln -sf ${IMAGE_NAME}.sdimg ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.sdimg
}
