inherit image_types

UUU_IMAGE_DIR ?= "${WORKDIR}/uuuimage"
create_uuu_image() {
	rm -rf ${UUU_IMAGE_DIR}
	mkdir -p ${UUU_IMAGE_DIR}

	# Copy tools
	install -m0755 "${RECIPE_SYSROOT}${libdir}/uuu/uuu" ${UUU_IMAGE_DIR}
	install -m0755 "${RECIPE_SYSROOT_NATIVE}${datadir}/uuu/emmc_burn_all.lst" ${UUU_IMAGE_DIR}
	install -m0755 "${RECIPE_SYSROOT_NATIVE}${datadir}/uuu/spl_boot.lst" ${UUU_IMAGE_DIR}

	# Copy images over with filenames needed by upstream emmc_burn_all
	# Flash execute (after extract):
	#   sudo ./uuu ./emmc_burn_all.lst 
	cp ${DEPLOY_DIR_IMAGE}/imx-boot ${UUU_IMAGE_DIR}/_flash.bin
	cp ${IMGDEPLOYDIR}/${IMAGE_LINK_NAME}.wic ${UUU_IMAGE_DIR}/_image

	cd ${UUU_IMAGE_DIR}
        ${IMAGE_CMD_TAR} --sparse --numeric-owner --transform="s,^\./,," -cf- . | gzip -f -9 -n -c --rsyncable > ${IMGDEPLOYDIR}/${IMAGE_NAME}.uuu.tar.gz
	ln -sf ${IMAGE_NAME}.uuu.tar.gz ${IMGDEPLOYDIR}/${IMAGE_LINK_NAME}.uuu.tar.gz
}

IMAGE_CMD:uuu = "create_uuu_image"
do_image_uuu[depends] += "imx-boot:do_deploy"

IMAGE_TYPEDEP:uuu += "wic"
IMAGE_FSTYPES:append = " uuu"

# uuu-bin is a prebuilt binary suitable to pack
# uuu-native is used to get emmc_burn_all.lst
DEPENDS:append = " uuu-bin uuu-native"
