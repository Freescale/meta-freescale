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
