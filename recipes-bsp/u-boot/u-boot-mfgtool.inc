# Produces a Manufacturing Tool compatible U-Boot
#
# This makes a separated binary set for Manufacturing Tool use
# without clobbering the U-Boot used for normal use.
#
# This file must to be included after the original u-boot.inc file,
# as it overrides the need values.
#
# Copyright (C) 2014 O.S. Systems Software LTDA.

# Adjust provides
PROVIDES = "u-boot-mfgtool"

# Use 'mfgtool' config
UBOOT_CONFIG = "mfgtool"

# Add 'mfgtool' suffix
UBOOT_IMAGE = "u-boot-${MACHINE}-mfgtool-${PV}-${PR}.${UBOOT_SUFFIX}"
UBOOT_SYMLINK = "u-boot-${MACHINE}-mfgtool.${UBOOT_SUFFIX}"
SPL_IMAGE = "${SPL_BINARY}-${MACHINE}-mfgtool-${PV}-${PR}"
SPL_SYMLINK = "${SPL_BINARY}-mfgtool-${MACHINE}"
