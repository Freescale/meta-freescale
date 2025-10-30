SUMMARY = "i.MX Optional Execution Image"
DESCRIPTION = "\
The Optional Executable Image (OEI) is an optional plugin loaded and executed \
by Cortex-M processor ROM on many NXP i.MX processors. The Cortex-M is the \
boot core, runs the boot ROM which loads the OEI, and then branches to the \
OEI. The OEI then configures some aspects of the hardware such as DDR config, \
init TCM ECC, etc. There could be multiple OEI images in the boot container. \
After execution of OEI, the processor returns to ROM execution."
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=59530bdf33659b29e73d4adb9f9f6552"

SRC_URI = "${IMX_OEI_SRC};branch=${SRCBRANCH}"
IMX_OEI_SRC ?= "git://github.com/nxp-imx/imx-oei.git;protocol=https"
SRCBRANCH = "master"
SRCREV = "1a572a640ef8d6883e8ca39744cd6d2d5dbed678"

require imx-oei.inc
