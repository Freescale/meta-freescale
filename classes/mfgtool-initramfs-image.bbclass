# Generates a Manufacturing Tool Initramfs image
#
# This generates the initramfs used for the installation process. The
# image provides the utilities which are used, in the target, during
# the process and receive the commands from the MfgTool application.
#
# Copyright 2014-2017 (C) O.S. Systems Software LTDA.

DEPENDS += "u-boot-mfgtool linux-mfgtool"

FEATURE_PACKAGES_mtd = "packagegroup-fsl-mfgtool-mtd"
FEATURE_PACKAGES_extfs = "packagegroup-fsl-mfgtool-extfs"
FEATURE_PACKAGES_f2fs = "packagegroup-fsl-mfgtool-f2fs"

ZSTD_COMPRESSION_LEVEL ?= "-10"
IMAGE_FSTYPES ?= "cpio.zst.u-boot"
IMAGE_FSTYPES:mxs-generic-bsp ?= "cpio.gz.u-boot"
IMAGE_ROOTFS_SIZE ?= "8192"

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
