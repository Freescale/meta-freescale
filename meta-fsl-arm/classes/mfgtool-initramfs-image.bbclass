# Generates a Manufacturing Tool Initramfs image
#
# This generates the initramfs used for the installation process. The
# image provides the utilities which are used, in the target, during
# the process and receive the commands from the MfgTool application.
#
# Copyright 2014 (C) O.S. Systems Software LTDA.

DEPENDS += "u-boot-mfgtool linux-mfgtool"

FEATURE_PACKAGES_mtd = "packagegroup-fsl-mfgtool-mtd"
FEATURE_PACKAGES_extfs = "packagegroup-fsl-mfgtool-extfs"

IMAGE_FSTYPES = "cpio.gz.u-boot"
IMAGE_ROOTFS_SIZE ?= "8192"
IMAGE_CLASSES = "image_types_uboot"

# Filesystems enabled by default
DEFAULT_FS_SUPPORT = " \
    mtd \
    extfs \
"

IMAGE_FEATURES = " \
    ${DEFAULT_FS_SUPPORT} \
    \
    read-only-rootfs \
"

# Avoid installation of syslog
BAD_RECOMMENDATIONS += "busybox-syslog"

# Avoid static /dev
USE_DEVFS = "1"

inherit core-image

CORE_IMAGE_BASE_INSTALL = " \
    ${CORE_IMAGE_EXTRA_INSTALL} \
"
