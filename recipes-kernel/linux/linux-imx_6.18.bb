# Copyright 2013-2016 Freescale Semiconductor
# Copyright 2017-2026 NXP
# Copyright 2018 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)
#
# SPDX-License-Identifier: MIT
#

SUMMARY = "Linux Kernel provided and supported by NXP"
DESCRIPTION = "Linux Kernel provided and supported by NXP with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

DEPENDS += "coreutils-native"

SRC_URI = "${LINUX_IMX_SRC}"
LINUX_IMX_SRC ?= "git://github.com/nxp-imx/linux-imx.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "lf-6.18.y"
KBRANCH = "${SRCBRANCH}"
LOCALVERSION = "-1.0.0"
SRCREV = "f49f45233f7b10006ce7e9c826ee882bb14ac8b5"

# PV is defined in the base in linux-imx.inc file and uses the LINUX_VERSION definition
# required by kernel-yocto.bbclass.
#
# LINUX_VERSION define should match to the kernel version referenced by SRC_URI and
# should be updated once patchlevel is merged.
LINUX_VERSION = "6.18.2"

KBUILD_DEFCONFIG:mx6-generic-bsp = "imx_v7_defconfig"
KBUILD_DEFCONFIG:mx7-generic-bsp = "imx_v7_defconfig"
KBUILD_DEFCONFIG:mx8-generic-bsp = "imx_v8_defconfig"
KBUILD_DEFCONFIG:mx9-generic-bsp = "imx_v8_defconfig"

DEFAULT_PREFERENCE = "1"

python __anonymous () {
    import bb
    # Fail fast if DELTA_KERNEL_DEFCONFIG is present in the datastore (even if empty)
    if "DELTA_KERNEL_DEFCONFIG" in d.keys():
        val = d.getVar("DELTA_KERNEL_DEFCONFIG", expand=False)
        bb.error(f"Detected deprecated/unsupported variable 'DELTA_KERNEL_DEFCONFIG' (value: '{val}').")
        bb.fatal("Please remove 'DELTA_KERNEL_DEFCONFIG' and use supported kernel configuration methods, "
                 "e.g., configuration fragments via kernel-yocto or a maintained defconfig.")
}

do_deploy:append() {
    if [ ${@bb.utils.filter('UBOOT_CONFIG', 'crrm', d)} ]; then
        baseName=${KERNEL_IMAGETYPE}-${KERNEL_IMAGE_NAME}
        gzip -c ${DEPLOYDIR}/$baseName${KERNEL_IMAGE_BIN_EXT} > \
            ${DEPLOYDIR}/$baseName${KERNEL_IMAGE_BIN_EXT}.gz
        ln -sf $baseName${KERNEL_IMAGE_BIN_EXT}.gz $deployDir/${KERNEL_IMAGETYPE}.gz
        # FIXME: For now, the CRRM kernel is just a copy of the regular kernel
        ln -sf $baseName${KERNEL_IMAGE_BIN_EXT}    $deployDir/${KERNEL_IMAGETYPE}_crrm
        ln -sf $baseName${KERNEL_IMAGE_BIN_EXT}.gz $deployDir/${KERNEL_IMAGETYPE}_crrm.gz
    fi
}

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
