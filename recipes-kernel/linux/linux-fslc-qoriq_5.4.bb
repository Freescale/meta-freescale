# Released under the MIT license (see COPYING.MIT for the terms)

HOMEPAGE = "https://github.com/Freescale/linux-fslc"

require recipes-kernel/linux/linux-qoriq.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

LINUX_VERSION = "5.4.92"

SRCBRANCH = "5.4.y+qoriq+fslc"
SRC_URI := "git://github.com/Freescale/linux-fslc.git;branch=${SRCBRANCH};protocol=https"
SRCREV = "11d4722c637a77c6e1c9a8eeec091f1588f6b3f3"
