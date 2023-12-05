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
                if [ "${SRCREV}" = "INVALID" ]; then
                        hash=${SRCREV_machine}
                else
                        hash=${SRCREV}
                fi
                if [ "$hash" = "AUTOINC" ]; then
                        branch=`git --git-dir=${S}/.git  symbolic-ref --short -q HEAD`
                        head=`git --git-dir=${S}/.git rev-parse --verify --short origin/${branch} 2> /dev/null`
                else
                        head=`git --git-dir=${S}/.git rev-parse --verify --short $hash 2> /dev/null`
                fi
                patches=`git --git-dir=${S}/.git rev-list --count $head..HEAD 2> /dev/null`
                printf "%s%s%s%s" +g $head +p $patches > ${S}/.scmversion
                printf "%s%s%s%s" +g $head +p $patches > ${B}/.scmversion
        else
		printf "%s" "${UBOOT_LOCALVERSION}" > ${S}/.scmversion
		printf "%s" "${UBOOT_LOCALVERSION}" > ${B}/.scmversion
	fi
}
