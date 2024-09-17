# Create a tagged boot partition file for the SD card image file. The tag
# contains the size of the boot partition image so UUU can easily find
# the end of it in the SD card image file.
#
# IMPORTANT: The tagged boot partition file should never be used directly with
#            UUU, as it can cause UUU to hang.

UUU_BOOTLOADER                 = "${UBOOT_BINARY}"
UUU_BOOTLOADER:mx8-generic-bsp = "${@d.getVar('UBOOT_PROVIDES_BOOT_CONTAINER') == '0' and 'imx-boot' or 'flash.bin'}"
UUU_BOOTLOADER:mx9-generic-bsp = "${@d.getVar('UBOOT_PROVIDES_BOOT_CONTAINER') == '0' and 'imx-boot' or 'flash.bin'}"

do_deploy:append() {
    if [ "${UUU_BOOTLOADER}" != "" ]; then
        cp ${DEPLOYDIR}/${UUU_BOOTLOADER} \
           ${DEPLOYDIR}/${UUU_BOOTLOADER}.tagged
        stat -L -cUUUBURNXXOEUZX7+A-XY5601QQWWZ%sEND \
             ${DEPLOYDIR}/${UUU_BOOTLOADER}.tagged \
             >> ${DEPLOYDIR}/${UUU_BOOTLOADER}.tagged
    fi
}
