SRC_URI = "\
		${GNU_MIRROR}/binutils/binutils-${PV}.tar.bz2 \
		file://bin.e5500.patch \
		file://bin.e6500-2.patch \
		file://bin.e500mc_nop.patch \
		"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PR .= "+${DISTRO}.0"
