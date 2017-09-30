# -*- python -*-
# Linux kernel with DCD headers
#
# This new image type adds support to generate a Linux kernel with DCD
# headers. Essentially it adds support to directly boot a Linux kernel
# without a bootloader.
#
# As the Linux kernel will be boot directly, it required that the Device
# Tree is appended to the kernel image. To have the Linux kernel
# generated with the DCD headers, following steps must be followed:
#
#  - Linux kernel recipe must inherit kernel-imximage class;
#  - Device Tree appended Linux kernel must be used
#    (KERNEL_DEVICETREE_BUNDLE = "1");
#  - DCD header configuration must be provided (dcd.cfg or
#    dcd-<devicetree>.cfg);
#
# Copyright 2017 (C) O.S. Systems Software LTDA.

DEPENDS_append = ' u-boot-mkimage-native'

IMXIMAGE_ENTRYPOINT ?= "${UBOOT_ENTRYPOINT}"

imx_mkimage() {
	uboot-mkimage -n $1 -T imximage -e ${IMXIMAGE_ENTRYPOINT} -d $2 $2.imx
}

gen_imximage() {
	if [ -z "${IMXIMAGE_ENTRYPOINT}" ]; then
		bbfatal "IMXIMAGE_ENTRYPOINT must have a valid value"
	fi

	for DTB in ${KERNEL_DEVICETREE}; do
		DTB=`normalize_dtb "${DTB}"`
		DTB_EXT=${DTB##*.}
		DTB_BASE_NAME=`basename ${DTB} ."${DTB_EXT}"`
		base_name="zImage-"${KERNEL_IMAGE_BASE_NAME}
		symlink_name="zImage-"${KERNEL_IMAGE_SYMLINK_NAME}
		DTB_NAME=`echo ${base_name} | sed "s/${MACHINE}/${DTB_BASE_NAME}/g"`
		DTB_SYMLINK_NAME=`echo ${symlink_name} | sed "s/${MACHINE}/${DTB_BASE_NAME}/g"`
		for DCD in dcd-${DTB}.cfg dcd.cfg; do
			if [ -e "${WORKDIR}/${DCD}" ]; then
				if [ -e ${DEPLOYDIR}/${DTB_NAME}.${DTB_EXT}.bin ]; then
					imx_mkimage ${WORKDIR}/${DCD} ${DEPLOYDIR}/${DTB_NAME}.${DTB_EXT}.bin
					ln -sf ${DTB_NAME}.${DTB_EXT}.bin.imx ${DEPLOYDIR}/$type-${DTB_BASE_NAME}.${DTB_EXT}.bin.imx
				fi

				if [ -e ${DEPLOYDIR}/zImage-${INITRAMFS_BASE_NAME}-${DTB_BASE_NAME}.${DTB_EXT}.bin ]; then
					imx_mkimage ${WORKDIR}/${DCD} ${DEPLOYDIR}/zImage-${INITRAMFS_BASE_NAME}-${DTB_BASE_NAME}.${DTB_EXT}.bin
					ln -sf zImage-${INITRAMFS_BASE_NAME}-${DTB_BASE_NAME}.${DTB_EXT}.bin.imx \
					   ${DEPLOYDIR}/zImage-initramfs-${DTB_BASE_NAME}.${DTB_EXT}-${MACHINE}.bin.imx
				fi
			fi
		done
	done
}

do_deploy_append() {
	gen_imximage
}
