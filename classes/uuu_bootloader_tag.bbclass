# Append a tag to the bootloader image used in the SD card image. The tag
# contains the size of the bootloader image so UUU can easily find the end of
# the bootloader in the SD card image.
do_deploy:append() {
    if [ "${UUU_BOOTLOADER}" != "" ]; then
        cp ${DEPLOYDIR}/${UUU_BOOTLOADER}   ${DEPLOYDIR}/${UUU_BOOTLOADER_TAGGED}
        ln -sf ${UUU_BOOTLOADER_TAGGED}     ${DEPLOYDIR}/${UUU_BOOTLOADER}
        stat -L -cUUUBURNXXOEUZX7+A-XY5601QQWWZ%sEND ${DEPLOYDIR}/${UUU_BOOTLOADER} \
                                         >> ${DEPLOYDIR}/${UUU_BOOTLOADER}
    fi
}
