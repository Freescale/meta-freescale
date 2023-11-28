# Freescale U-Boot LOCALVERSION extension
#
# This allow to easy reuse of code between different U-Boot recipes
#
# The following options are supported:
#
#  SCMVERSION        Puts the Git hash in U-Boot local version
#  LOCALVERSION      Value used in LOCALVERSION (default to '+fslc')
#
# Copyright 2014 (C) O.S. Systems Software LTDA.

SCMVERSION ??= "y"
LOCALVERSION ??= "+fslc"

UBOOT_LOCALVERSION = "${LOCALVERSION}"

do_compile:prepend() {
	if [ "${SCMVERSION}" = "y" ]; then
		# Add GIT revision to the local version
		head=`cd ${S} ; git rev-parse --verify --short ${SRCREV} 2> /dev/null`
		patches=`cd ${S} ; git rev-list --count ${SRCREV}..HEAD 2> /dev/null`
		printf "%s%s%s%s%s" "${UBOOT_LOCALVERSION}" +g $head +p $patches > ${S}/.scmversion
		printf "%s%s%s%s%s" "${UBOOT_LOCALVERSION}" +g $head +p $patches > ${B}/.scmversion
    else
		printf "%s" "${UBOOT_LOCALVERSION}" > ${S}/.scmversion
		printf "%s" "${UBOOT_LOCALVERSION}" > ${B}/.scmversion
	fi
}
