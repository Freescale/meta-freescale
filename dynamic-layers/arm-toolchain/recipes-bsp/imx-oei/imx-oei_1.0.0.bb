SUMMARY = "i.MX Optional Execution Image"
DESCRIPTION = "\
The Optional Executable Image (OEI) is an optional plugin loaded and executed \
by Cortex-M processor ROM on many NXP i.MX processors. The Cortex-M is the \
boot core, runs the boot ROM which loads the OEI, and then branches to the \
OEI. The OEI then configures some aspects of the hardware such as DDR config, \
init TCM ECC, etc. There could be multiple OEI images in the boot container. \
After execution of OEI, the processor returns to ROM execution."
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=b66f32a90f9577a5a3255c21d79bc619"

SRC_URI = "${IMX_OEI_SRC};branch=${SRCBRANCH}"
IMX_OEI_SRC ?= "git://github.com/nxp-imx/imx-oei.git;protocol=https"
SRCBRANCH = "master"
SRCREV = "49bfaa93e9d1fe213866bcb9507927a59a9ede5a"

require imx-oei.inc
