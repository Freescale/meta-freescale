inherit features_check
REQUIRED_DISTRO_FEATURES:append:e6500 = " multiarch"

# BUILD_64BIT_KERNEL is this class's interface: a QorIQ PowerPC machine that
# sets it gets a 64-bit kernel on its 32-bit userspace, and in-tree only
# e6500.inc does.
#
# The default cross toolchain builds it: with multiarch, gcc-cross-powerpc is
# configured with --enable-targets=powerpc64 and binutils with
# --enable-64-bit-bfd, and the powerpc kernel Makefile passes -m64 to such a
# biarch compiler. Only the arch QA check has to accept the 64-bit objects.
ERROR_QA:remove:qoriq-ppc = "${@'arch' if d.getVar('BUILD_64BIT_KERNEL') == '1' else ''}"
