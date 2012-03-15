SRC_URI_fsl = "\
		${GNU_MIRROR}/binutils/binutils-${PV}.tar.bz2 \
		file://bin.e5500.patch \
		file://bin.e6500-2.patch \
		file://bin.e500mc_nop.patch \
		file://libtool-2.4-update_fsl.patch \
		file://binutils-2.19.1-ld-sysroot.patch \
		file://libiberty_path_fix.patch \
		file://binutils-poison.patch \
		file://libtool-rpath-fix.patch \
		"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PR_fsl .= "+${DISTRO}.4"
