#LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552 \
#                   file://COPYING3;md5=d32239bcb673463ab874e80d47fae504 \
#                   file://COPYING3.LIB;md5=6a6a8e020838b23406c81b19c1d46df6 \
#                   file://COPYING.LIB;md5=2d5025d4aa3495befef8f17206a5b0a1 \
#                   file://COPYING.RUNTIME;md5=fe60d87048567d4fe8c8a0ed2448bcc8"

BINV_fsl = "4.6.2"

SRCREV_fsl = "180516"
# ignore linaro backports since we are changing the SRCREV
GCC-4_6-BRANCH-LINARO-BACKPORTS_fsl = ""

SRC_URI_fsl = "svn://gcc.gnu.org/svn/gcc/branches;module=${BRANCH};proto=http \
           file://gcc-4.3.1-ARCH_FLAGS_FOR_TARGET.patch \
           file://gcc.fix_longversionstring.patch \
           file://gcc.rm_slow_tests.patch \
           file://gcc.fix_mingw32.patch \
           file://gcc.fix_cloogstatic2.patch \
           file://gcc.fix_build-with-cxx.patch \
	   file://gcc.e6500-FSF46.patch \
           file://gcc.ld_unaligned-460.patch \
           file://gcc.local_unaligned_altivec.patch \
           file://gcc.soft_float-460.patch \
           file://gcc.case_values.patch \
           file://gcc.builtin_isel.patch \
           file://gcc.experimental_move.patch \
           file://gcc.widen_types-46.patch \
           file://gcc.extelim-v3.patch \
           file://gcc.e5500_mfocr.patch \
           file://gcc.opt-array-offset.patch \
           file://gcc.load_on_store_bypass-462.patch \
           file://gcc.fix_constvector.patch \
           file://gcc.fix_MTWX51204-dwarf-vector-reg.patch \
           file://gcc.fix_ira-loop-pressure.patch \
	   file://optional_libstdc.patch \
	   file://gcc.remove_CCUNSmode_reference.patch \
	   file://gcc.check_path_validity.patch \
	   file://gcc.fix_header_issue.patch \
	   file://gcc.fix_SSIZE_MAX_undefine_issue.patch \
	   file://GPLUSPLUS_INCLUDE_DIR_with_sysroot_fsl.patch \
	   file://gcc.fix_min_max.patch \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PR_fsl .= "+${DISTRO}.2"
