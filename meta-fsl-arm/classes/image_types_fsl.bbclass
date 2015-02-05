inherit image_types

IMAGE_BOOTLOADER ?= "u-boot"

# Handle u-boot suffixes
UBOOT_SUFFIX ?= "bin"
UBOOT_SUFFIX_SDCARD ?= "${UBOOT_SUFFIX}"

#
# Handles i.MX mxs bootstream generation
#
MXSBOOT_NAND_ARGS ?= ""

# IMX Bootlets Linux bootstream
IMAGE_DEPENDS_linux.sb = "elftosb-native:do_populate_sysroot \
                          imx-bootlets:do_deploy \
                          virtual/kernel:do_deploy"
IMAGE_LINK_NAME_linux.sb = ""
IMAGE_CMD_linux.sb () {
	kernel_bin="`readlink ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${MACHINE}.bin`"
	kernel_dtb="`readlink ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${MACHINE}.dtb || true`"
	linux_bd_file=imx-bootlets-linux.bd-${MACHINE}
	if [ `basename $kernel_bin .bin` = `basename $kernel_dtb .dtb` ]; then
		# When using device tree we build a zImage with the dtb
		# appended on the end of the image
		linux_bd_file=imx-bootlets-linux.bd-dtb-${MACHINE}
		cat $kernel_bin $kernel_dtb \
		    > $kernel_bin-dtb
		rm -f ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${MACHINE}.bin-dtb
		ln -s $kernel_bin-dtb ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${MACHINE}.bin-dtb
	fi

	# Ensure the file is generated
	rm -f ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.linux.sb
	(cd ${DEPLOY_DIR_IMAGE}; elftosb -z -c $linux_bd_file -o ${IMAGE_NAME}.linux.sb)

	# Remove the appended file as it is only used here
	rm -f ${DEPLOY_DIR_IMAGE}/$kernel_bin-dtb
}

# IMX Bootlets barebox bootstream
IMAGE_DEPENDS_barebox.mxsboot-sdcard = "elftosb-native:do_populate_sysroot \
                                        u-boot-mxsboot-native:do_populate_sysroot \
                                        imx-bootlets:do_deploy \
                                        barebox:do_deploy"
IMAGE_CMD_barebox.mxsboot-sdcard () {
	barebox_bd_file=imx-bootlets-barebox_ivt.bd-${MACHINE}

	# Ensure the files are generated
	(cd ${DEPLOY_DIR_IMAGE}; rm -f ${IMAGE_NAME}.barebox.sb ${IMAGE_NAME}.barebox.mxsboot-sdcard; \
	 elftosb -f mx28 -z -c $barebox_bd_file -o ${IMAGE_NAME}.barebox.sb; \
	 mxsboot sd ${IMAGE_NAME}.barebox.sb ${IMAGE_NAME}.barebox.mxsboot-sdcard)
}

# U-Boot mxsboot generation to SD-Card
UBOOT_SUFFIX_SDCARD_mxs ?= "mxsboot-sdcard"
IMAGE_DEPENDS_uboot.mxsboot-sdcard = "u-boot-mxsboot-native:do_populate_sysroot \
                                      u-boot:do_deploy"
IMAGE_CMD_uboot.mxsboot-sdcard = "mxsboot sd ${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE}.${UBOOT_SUFFIX} \
                                             ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.uboot.mxsboot-sdcard"

IMAGE_DEPENDS_uboot.mxsboot-nand = "u-boot-mxsboot-native:do_populate_sysroot \
                                      u-boot:do_deploy"
IMAGE_CMD_uboot.mxsboot-nand = "mxsboot ${MXSBOOT_NAND_ARGS} nand \
                                             ${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE}.${UBOOT_SUFFIX} \
                                             ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.uboot.mxsboot-nand"

# Boot partition volume id
BOOTDD_VOLUME_ID ?= "Boot ${MACHINE}"

# Boot partition size [in KiB]
BOOT_SPACE ?= "8192"

# Barebox environment size [in KiB]
BAREBOX_ENV_SPACE ?= "512"

# Set alignment to 4MB [in KiB]
IMAGE_ROOTFS_ALIGNMENT = "4096"

IMAGE_DEPENDS_sdcard = "parted-native:do_populate_sysroot \
                        dosfstools-native:do_populate_sysroot \
                        mtools-native:do_populate_sysroot \
                        virtual/kernel:do_deploy \
                        ${@d.getVar('IMAGE_BOOTLOADER', True) and d.getVar('IMAGE_BOOTLOADER', True) + ':do_deploy' or ''}"

SDCARD = "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.sdcard"

SDCARD_GENERATION_COMMAND_mxs = "generate_mxs_sdcard"
SDCARD_GENERATION_COMMAND_mx25 = "generate_imx_sdcard"
SDCARD_GENERATION_COMMAND_mx5 = "generate_imx_sdcard"
SDCARD_GENERATION_COMMAND_mx6 = "generate_imx_sdcard"
SDCARD_GENERATION_COMMAND_vf60 = "generate_imx_sdcard"

#
# Create an image that can by written onto a SD card using dd for use
# with i.MX SoC family
#
# External variables needed:
#   ${SDCARD_ROOTFS}    - the rootfs image to incorporate
#   ${IMAGE_BOOTLOADER} - bootloader to use {u-boot, barebox}
#
# The disk layout used is:
#
#    0                      -> IMAGE_ROOTFS_ALIGNMENT         - reserved to bootloader (not partitioned)
#    IMAGE_ROOTFS_ALIGNMENT -> BOOT_SPACE                     - kernel and other data
#    BOOT_SPACE             -> SDIMG_SIZE                     - rootfs
#
#                                                     Default Free space = 1.3x
#                                                     Use IMAGE_OVERHEAD_FACTOR to add more space
#                                                     <--------->
#            4MiB               8MiB           SDIMG_ROOTFS                    4MiB
# <-----------------------> <----------> <----------------------> <------------------------------>
#  ------------------------ ------------ ------------------------ -------------------------------
# | IMAGE_ROOTFS_ALIGNMENT | BOOT_SPACE | ROOTFS_SIZE            |     IMAGE_ROOTFS_ALIGNMENT    |
#  ------------------------ ------------ ------------------------ -------------------------------
# ^                        ^            ^                        ^                               ^
# |                        |            |                        |                               |
# 0                      4096     4MiB +  8MiB       4MiB +  8Mib + SDIMG_ROOTFS   4MiB +  8MiB + SDIMG_ROOTFS + 4MiB
generate_imx_sdcard () {
	# Create partition table
	parted -s ${SDCARD} mklabel msdos
	parted -s ${SDCARD} unit KiB mkpart primary fat32 ${IMAGE_ROOTFS_ALIGNMENT} $(expr ${IMAGE_ROOTFS_ALIGNMENT} \+ ${BOOT_SPACE_ALIGNED})
	parted -s ${SDCARD} unit KiB mkpart primary $(expr  ${IMAGE_ROOTFS_ALIGNMENT} \+ ${BOOT_SPACE_ALIGNED}) $(expr ${IMAGE_ROOTFS_ALIGNMENT} \+ ${BOOT_SPACE_ALIGNED} \+ $ROOTFS_SIZE)
	parted ${SDCARD} print

	# Burn bootloader
	case "${IMAGE_BOOTLOADER}" in
		imx-bootlets)
		bberror "The imx-bootlets is not supported for i.MX based machines"
		exit 1
		;;
		u-boot)
		if [ -n "${SPL_BINARY}" ]; then
			dd if=${DEPLOY_DIR_IMAGE}/${SPL_BINARY} of=${SDCARD} conv=notrunc seek=2 bs=512
			dd if=${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE}.${UBOOT_SUFFIX_SDCARD} of=${SDCARD} conv=notrunc seek=69 bs=1K
		else
			dd if=${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE}.${UBOOT_SUFFIX_SDCARD} of=${SDCARD} conv=notrunc seek=2 bs=512
		fi
		;;
		barebox)
		dd if=${DEPLOY_DIR_IMAGE}/barebox-${MACHINE}.bin of=${SDCARD} conv=notrunc seek=1 skip=1 bs=512
		dd if=${DEPLOY_DIR_IMAGE}/bareboxenv-${MACHINE}.bin of=${SDCARD} conv=notrunc seek=1 bs=512k
		;;
		"")
		;;
		*)
		bberror "Unknown IMAGE_BOOTLOADER value"
		exit 1
		;;
	esac

	# Create boot partition image
	BOOT_BLOCKS=$(LC_ALL=C parted -s ${SDCARD} unit b print \
	                  | awk '/ 1 / { print substr($4, 1, length($4 -1)) / 1024 }')
	mkfs.vfat -n "${BOOTDD_VOLUME_ID}" -S 512 -C ${WORKDIR}/boot.img $BOOT_BLOCKS
	mcopy -i ${WORKDIR}/boot.img -s ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${MACHINE}.bin ::/${KERNEL_IMAGETYPE}

	# Copy boot scripts
	for item in ${BOOT_SCRIPTS}; do
		src=`echo $item | awk -F':' '{ print $1 }'`
		dst=`echo $item | awk -F':' '{ print $2 }'`

		mcopy -i ${WORKDIR}/boot.img -s ${DEPLOY_DIR_IMAGE}/$src ::/$dst
	done

	# Copy device tree file
	if test -n "${KERNEL_DEVICETREE}"; then
		for DTS_FILE in ${KERNEL_DEVICETREE}; do
			DTS_BASE_NAME=`basename ${DTS_FILE} | awk -F "." '{print $1}'`
			if [ -e "${KERNEL_IMAGETYPE}-${DTS_BASE_NAME}.dtb" ]; then
				kernel_bin="`readlink ${KERNEL_IMAGETYPE}-${MACHINE}.bin`"
				kernel_bin_for_dtb="`readlink ${KERNEL_IMAGETYPE}-${DTS_BASE_NAME}.dtb | sed "s,$DTS_BASE_NAME,${MACHINE},g;s,\.dtb$,.bin,g"`"
				if [ $kernel_bin = $kernel_bin_for_dtb ]; then
					mcopy -i ${WORKDIR}/boot.img -s ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${DTS_BASE_NAME}.dtb ::/${DTS_BASE_NAME}.dtb
				fi
			fi
		done
	fi

	# Burn Partition
	dd if=${WORKDIR}/boot.img of=${SDCARD} conv=notrunc seek=1 bs=$(expr ${IMAGE_ROOTFS_ALIGNMENT} \* 1024) && sync && sync
	dd if=${SDCARD_ROOTFS} of=${SDCARD} conv=notrunc seek=1 bs=$(expr ${BOOT_SPACE_ALIGNED} \* 1024 + ${IMAGE_ROOTFS_ALIGNMENT} \* 1024) && sync && sync
}

#
# Create an image that can by written onto a SD card using dd for use
# with i.MXS SoC family
#
# External variables needed:
#   ${SDCARD_ROOTFS}    - the rootfs image to incorporate
#   ${IMAGE_BOOTLOADER} - bootloader to use {imx-bootlets, u-boot}
#
generate_mxs_sdcard () {
	# Create partition table
	parted -s ${SDCARD} mklabel msdos

	case "${IMAGE_BOOTLOADER}" in
		imx-bootlets)
		# The disk layout used is:
		#
		#    0                      -> 1024                           - Unused (not partitioned)
		#    1024                   -> BOOT_SPACE                     - kernel and other data (bootstream)
		#    BOOT_SPACE             -> SDIMG_SIZE                     - rootfs
		#
		#                                     Default Free space = 1.3x
		#                                     Use IMAGE_OVERHEAD_FACTOR to add more space
		#                                     <--------->
		#    1024        8MiB          SDIMG_ROOTFS                    4MiB
		# <-------> <----------> <----------------------> <------------------------------>
		#  --------------------- ------------------------ -------------------------------
		# | Unused | BOOT_SPACE | ROOTFS_SIZE            |     IMAGE_ROOTFS_ALIGNMENT    |
		#  --------------------- ------------------------ -------------------------------
		# ^        ^            ^                        ^                               ^
		# |        |            |                        |                               |
		# 0      1024      1024 + 8MiB       1024 + 8Mib + SDIMG_ROOTFS      1024 + 8MiB + SDIMG_ROOTFS + 4MiB
		parted -s ${SDCARD} unit KiB mkpart primary 1024 $(expr ${IMAGE_ROOTFS_ALIGNMENT} \+ ${BOOT_SPACE_ALIGNED})
		parted -s ${SDCARD} unit KiB mkpart primary $(expr ${IMAGE_ROOTFS_ALIGNMENT} \+ ${BOOT_SPACE_ALIGNED}) $(expr ${IMAGE_ROOTFS_ALIGNMENT} \+ ${BOOT_SPACE_ALIGNED} \+ $ROOTFS_SIZE)

		# Empty 4 blocks from boot partition
		dd if=/dev/zero of=${SDCARD} conv=notrunc seek=2048 count=4

		# Write the bootstream in (2048 + 4) blocks
		dd if=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.linux.sb of=${SDCARD} conv=notrunc seek=2052
		;;
		u-boot)
		# The disk layout used is:
		#
		#    1M - 2M                  - reserved to bootloader and other data
		#    2M - BOOT_SPACE          - kernel
		#    BOOT_SPACE - SDCARD_SIZE - rootfs
		#
		# The disk layout used is:
		#
		#    1M                     -> 2M                             - reserved to bootloader and other data
		#    2M                     -> BOOT_SPACE                     - kernel and other data
		#    BOOT_SPACE             -> SDIMG_SIZE                     - rootfs
		#
		#                                                        Default Free space = 1.3x
		#                                                        Use IMAGE_OVERHEAD_FACTOR to add more space
		#                                                        <--------->
		#            4MiB                8MiB             SDIMG_ROOTFS                    4MiB
		# <-----------------------> <-------------> <----------------------> <------------------------------>
		#  ---------------------------------------- ------------------------ -------------------------------
		# |      |      |                          |ROOTFS_SIZE             |     IMAGE_ROOTFS_ALIGNMENT    |
		#  ---------------------------------------- ------------------------ -------------------------------
		# ^      ^      ^          ^               ^                        ^                               ^
		# |      |      |          |               |                        |                               |
		# 0     1M     2M         4M        4MiB + BOOTSPACE   4MiB + BOOTSPACE + SDIMG_ROOTFS   4MiB + BOOTSPACE + SDIMG_ROOTFS + 4MiB
		#
		parted -s ${SDCARD} unit KiB mkpart primary 1024 2048
		parted -s ${SDCARD} unit KiB mkpart primary 2048 $(expr ${IMAGE_ROOTFS_ALIGNMENT} \+ ${BOOT_SPACE_ALIGNED})
		parted -s ${SDCARD} unit KiB mkpart primary $(expr  ${IMAGE_ROOTFS_ALIGNMENT} \+ ${BOOT_SPACE_ALIGNED}) $(expr ${IMAGE_ROOTFS_ALIGNMENT} \+ ${BOOT_SPACE_ALIGNED} \+ $ROOTFS_SIZE)

		dd if=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.uboot.mxsboot-sdcard of=${SDCARD} conv=notrunc seek=1 bs=$(expr 1024 \* 1024)
		BOOT_BLOCKS=$(LC_ALL=C parted -s ${SDCARD} unit b print \
	        | awk '/ 2 / { print substr($4, 1, length($4 -1)) / 1024 }')

		mkfs.vfat -n "${BOOTDD_VOLUME_ID}" -S 512 -C ${WORKDIR}/boot.img $BOOT_BLOCKS
		mcopy -i ${WORKDIR}/boot.img -s ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${MACHINE}.bin ::/${KERNEL_IMAGETYPE}
		if test -n "${KERNEL_DEVICETREE}"; then
			for DTS_FILE in ${KERNEL_DEVICETREE}; do
				DTS_BASE_NAME=`basename ${DTS_FILE} | awk -F "." '{print $1}'`
				if [ -e "${KERNEL_IMAGETYPE}-${DTS_BASE_NAME}.dtb" ]; then
					kernel_bin="`readlink ${KERNEL_IMAGETYPE}-${MACHINE}.bin`"
					kernel_bin_for_dtb="`readlink ${KERNEL_IMAGETYPE}-${DTS_BASE_NAME}.dtb | sed "s,$DTS_BASE_NAME,${MACHINE},g;s,\.dtb$,.bin,g"`"
					if [ $kernel_bin = $kernel_bin_for_dtb ]; then
						mcopy -i ${WORKDIR}/boot.img -s ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${DTS_BASE_NAME}.dtb ::/${DTS_BASE_NAME}.dtb
					fi
				fi
			done
		fi

		dd if=${WORKDIR}/boot.img of=${SDCARD} conv=notrunc seek=2 bs=$(expr 1024 \* 1024)
		;;
		barebox)
		# BAREBOX_ENV_SPACE is taken on BOOT_SPACE_ALIGNED but it doesn't really matter as long as the rootfs is aligned
		parted -s ${SDCARD} unit KiB mkpart primary 1024 $(expr ${IMAGE_ROOTFS_ALIGNMENT} \+ ${BOOT_SPACE_ALIGNED} - ${BAREBOX_ENV_SPACE})
		parted -s ${SDCARD} unit KiB mkpart primary $(expr ${IMAGE_ROOTFS_ALIGNMENT} \+ ${BOOT_SPACE_ALIGNED} - ${BAREBOX_ENV_SPACE}) $(expr ${IMAGE_ROOTFS_ALIGNMENT} \+ ${BOOT_SPACE_ALIGNED})
		parted -s ${SDCARD} unit KiB mkpart primary $(expr ${IMAGE_ROOTFS_ALIGNMENT} \+ ${BOOT_SPACE_ALIGNED}) $(expr ${IMAGE_ROOTFS_ALIGNMENT} \+ ${BOOT_SPACE_ALIGNED} \+ $ROOTFS_SIZE)

		dd if=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.barebox.mxsboot-sdcard of=${SDCARD} conv=notrunc seek=1 bs=$(expr 1024 \* 1024)
		dd if=${DEPLOY_DIR_IMAGE}/bareboxenv-${MACHINE}.bin of=${SDCARD} conv=notrunc seek=$(expr ${IMAGE_ROOTFS_ALIGNMENT} \+ ${BOOT_SPACE_ALIGNED} - ${BAREBOX_ENV_SPACE}) bs=1024
		;;
		*)
		bberror "Unknown IMAGE_BOOTLOADER value"
		exit 1
		;;
	esac

	# Change partition type for mxs processor family
	bbnote "Setting partition type to 0x53 as required for mxs' SoC family."
	echo -n S | dd of=${SDCARD} bs=1 count=1 seek=450 conv=notrunc

	parted ${SDCARD} print

	dd if=${SDCARD_ROOTFS} of=${SDCARD} conv=notrunc seek=1 bs=$(expr ${BOOT_SPACE_ALIGNED} \* 1024 + ${IMAGE_ROOTFS_ALIGNMENT} \* 1024) && sync && sync
}

IMAGE_CMD_sdcard () {
	if [ -z "${SDCARD_ROOTFS}" ]; then
		bberror "SDCARD_ROOTFS is undefined. To use sdcard image from Freescale's BSP it needs to be defined."
		exit 1
	fi

	# Align boot partition and calculate total SD card image size
	BOOT_SPACE_ALIGNED=$(expr ${BOOT_SPACE} + ${IMAGE_ROOTFS_ALIGNMENT} - 1)
	BOOT_SPACE_ALIGNED=$(expr ${BOOT_SPACE_ALIGNED} - ${BOOT_SPACE_ALIGNED} % ${IMAGE_ROOTFS_ALIGNMENT})
	SDCARD_SIZE=$(expr ${IMAGE_ROOTFS_ALIGNMENT} + ${BOOT_SPACE_ALIGNED} + $ROOTFS_SIZE + ${IMAGE_ROOTFS_ALIGNMENT})

	# Initialize a sparse file
	dd if=/dev/zero of=${SDCARD} bs=1 count=0 seek=$(expr 1024 \* ${SDCARD_SIZE})

	${SDCARD_GENERATION_COMMAND}
}

# The sdcard requires the rootfs filesystem to be built before using
# it so we must make this dependency explicit.
IMAGE_TYPEDEP_sdcard = "${@d.getVar('SDCARD_ROOTFS', 1).split('.')[-1]}"
