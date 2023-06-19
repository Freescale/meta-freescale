# Freescale Kernel LOCALVERSION extension
#
# This allow to easy reuse of code between different kernel recipes
#
# The following options are supported:
#
#  SCMVERSION        Puts the Git hash in kernel local version
#  LOCALVERSION      Value used in LOCALVERSION (default to '+fslc')
#
# Copyright 2014, 2015 (C) O.S. Systems Software LTDA.

SCMVERSION ??= "y"
LOCALVERSION ??= "+fslc"

# LINUX_VERSION_EXTENSION is used as CONFIG_LOCALVERSION by kernel-yocto class
LINUX_VERSION_EXTENSION ?= "${LOCALVERSION}"

do_kernel_localversion[dirs] += "${S} ${B}"
do_kernel_localversion() {

	# Fallback for recipes not able to use LINUX_VERSION_EXTENSION
	if [ "${@bb.data.inherits_class('kernel-yocto', d)}" = "False" ]; then
		echo 'CONFIG_LOCALVERSION="${LOCALVERSION}"' >> ${B}/.config
	fi

	if [ "${SCMVERSION}" = "y" ]; then
		# Add GIT revision to the local version
		head=`git --git-dir=${S}/.git rev-parse --verify --short HEAD 2> /dev/null`
		printf "%s%s" +g $head > ${S}/.scmversion

		sed -i -e "/CONFIG_LOCALVERSION_AUTO[ =]/d" ${B}/.config
		echo "CONFIG_LOCALVERSION_AUTO=y" >> ${B}/.config
	fi
}

addtask kernel_localversion before do_configure after do_patch do_kernel_configme
