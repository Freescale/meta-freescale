# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Copyright (C) 2017-2020 NXP

require recipes-bsp/u-boot/u-boot.inc
require u-boot-imx-common.inc

PROVIDES += "u-boot"

do_deploy_append_mx8m() {
    # Deploy the mkimage, u-boot-nodtb.bin and fsl-imx8m*-XX.dtb for mkimage to generate boot binary
    if [ -n "${UBOOT_CONFIG}" ]
    then
        for config in ${UBOOT_MACHINE}; do
            i=$(expr $i + 1);
            for type in ${UBOOT_CONFIG}; do
                j=$(expr $j + 1);
                if [ $j -eq $i ]
                then
                    install -d ${DEPLOYDIR}/${BOOT_TOOLS}
                    install -m 0777 ${B}/${config}/u-boot-nodtb.bin  ${DEPLOYDIR}/${BOOT_TOOLS}/u-boot-nodtb.bin-${MACHINE}-${type}

                    if [ "${@d.getVarFlags('UBOOT_DTB_NAME')}" = "None" ]; then
                        UBOOT_DTB_NAME_FLAGS="${type}:${UBOOT_DTB_NAME}"
                    else
                        UBOOT_DTB_NAME_FLAGS="${@' '.join(flag + ':' + dtb for flag, dtb in (d.getVarFlags('UBOOT_DTB_NAME')).items()) if d.getVarFlags('UBOOT_DTB_NAME') is not None else '' }"
                    fi

                    for key_value in ${UBOOT_DTB_NAME_FLAGS}; do
                        local type_key="${key_value%%:*}"
                        local dtb_name="${key_value#*:}"
                        if [ "$type_key" = "$type" ]
                        then
                            bbnote "UBOOT_CONFIG = $type, UBOOT_DTB_NAME = $dtb_name"
                            install -m 0777 ${B}/${config}/arch/arm/dts/${dtb_name}  ${DEPLOYDIR}/${BOOT_TOOLS}/${dtb_name}-${type}
                        fi
                        unset type_key
                        unset dtb_name
                    done

                    unset UBOOT_DTB_NAME_FLAGS
                fi
            done
            unset  j
        done
        unset  i
    fi
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
