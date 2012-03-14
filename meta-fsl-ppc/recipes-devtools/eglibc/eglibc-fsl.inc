SRC_URI_fsl = "svn://www.eglibc.org/svn/branches/;module=${EGLIBC_BRANCH};proto=http \
		file://glibc.undefined_static.patch \
		file://glibc.fixgcc4.6.patch \
		file://glibc.readv_proto.patch \
		file://glibc.fix_sqrt.patch \
		file://glibc.fix_prof.patch \
		file://glibc.e500mc_subspecies_of_powerpc_is_not_supported.patch \
		file://etc/ld.so.conf \
		file://generate-supported.mk \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PR_fsl .= "+${DISTRO}.0"
