# Copyright (C) 2012, 2015 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

inherit kernel-yocto kernel fsl-kernel-localversion fsl-vivante-kernel-driver-handler

# Put a local version until we have a true SRCREV to point to
LOCALVERSION ?= ""
SCMVERSION ?= "y"
SRCBRANCH ?= ""

# Allow use of kernel as linux-mfgtool provider.
PROVIDES += "linux-mfgtool"

# Set the PV to the correct kernel version to satisfy the kernel version sanity check
PV = "${LINUX_VERSION}+git${SRCPV}"

SRC_URI = "git://github.com/nxp-imx/linux-imx;protocol=https;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

# Tell to kernel class that we would like to use our defconfig to configure the kernel.
# Otherwise, the --allnoconfig would be used per default which leads to mis-configured
# kernel.
#
# This behavior happens when a defconfig is provided, the kernel-yocto configuration
# uses the filename as a trigger to use a 'allnoconfig' baseline before merging
# the defconfig into the build.
#
# If the defconfig file was created with make_savedefconfig, not all options are
# specified, and should be restored with their defaults, not set to 'n'.
# To properly expand a defconfig like this, we need to specify: KCONFIG_MODE="--alldefconfig"
# in the kernel recipe include.
KCONFIG_MODE="--alldefconfig"

# We need to pass it as param since kernel might support more then one
# machine, with different entry points
KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

# A function to strip the new 32-bit dtb sub-folders in KERNEL_DEVICETREE
# for older kernel builds.
# Set KERNEL_DEVICETREE_32BIT_COMPATIBILITY_UPDATE = "1" to enable.
KERNEL_DEVICETREE_32BIT_COMPATIBILITY_UPDATE ?= "0"
python kernel_devicetree_32bit_compatibility_update() {
    import os.path
    import re
    if d.getVar('KERNEL_DEVICETREE_32BIT_COMPATIBILITY_UPDATE') != "1" or d.getVar('TUNE_ARCH') != "arm":
        return
    input = d.getVar('KERNEL_DEVICETREE').split()
    output = ""
    stripped = ""
    for original in input:
        if re.match("^.*/", original):
            stripped = os.path.basename(original)
            output += stripped + " "
            bb.debug(1, "Devicetrees are moved to sub-folder, stripping the sub-folder for older kernel: %s -> %s" % (original, stripped))
        else:
            output += original + " "
    if stripped:
        bb.warn("Updating KERNEL_DEVICETREE, removing sub-folders for older kernel. Use -D for more details. Set KERNEL_DEVICETREE_32BIT_COMPATIBILITY_UPDATE = \"0\" to disable.")
        d.setVar('KERNEL_DEVICETREE', output)
}
addhandler kernel_devicetree_32bit_compatibility_update
kernel_devicetree_32bit_compatibility_update[eventmask] = "bb.event.RecipeParsed"
