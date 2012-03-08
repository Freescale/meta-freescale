#
# Create an image that can by written onto a SD card using dd.
#
# External variables needed:
#   ${ROOTFS} - the rootfs image to incorporate

# Add the fstypes we need
IMAGE_FSTYPES += "bbsdimg"

# Ensure required utilities are present
IMAGE_DEPENDS_bbsdimg = "parted-native virtual/kernel barebox"

# Default to 2GiB images
BBSDIMG_SIZE ?= "2000"

# Addional space for boot partition
BOOT_SPACE ?= "5M"

IMAGE_CMD_bbsdimg () {
	TMP=${WORKDIR}/tmp
	SDIMG=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.bbsdimg

	if [ -f ${SDIMG} ]; then
		rm ${SDIMG}
	fi
	dd if=/dev/zero of=${SDIMG} bs=$(expr 1000 \* 1000) count=${BBSDIMG_SIZE}

	# Create partition table
	parted -s ${SDIMG} mklabel msdos
	parted -s ${SDIMG} mkpart primary ${BOOT_SPACE} 100%
	parted ${SDIMG} print

	dd if=${DEPLOY_DIR_IMAGE}/barebox-${MACHINE}.bin of=${SDIMG} conv=notrunc seek=1 skip=1 bs=512
	dd if=${DEPLOY_DIR_IMAGE}/bareboxenv-${MACHINE}.bin of=${SDIMG} conv=notrunc seek=1 bs=512k
	dd if=${DEPLOY_DIR_IMAGE}/uImage-${MACHINE}.bin of=${SDIMG} conv=notrunc seek=1 bs=1M
	dd if=${ROOTFS} of=${SDIMG} conv=notrunc seek=1 bs=${BOOT_SPACE}
}
