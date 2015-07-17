inherit distro_features_check
REQUIRED_DISTRO_FEATURES_e6500 += "multiarch"

python () {
    promote_kernel = d.getVar('BUILD_64BIT_KERNEL')
    if promote_kernel == "1":
        sys_multilib = 'powerpc64' + d.getVar('TARGET_VENDOR') + 'mllib64-' + d.getVar('HOST_OS')
        tc_options = d.getVar('TOOLCHAIN_OPTIONS') + '/../lib64-' + d.getVar("MACHINE")
        d.setVar('DEPENDS_append', ' lib64-gcc-cross-powerpc64 lib64-libgcc')
        d.setVar('PATH_append', ':' + d.getVar('STAGING_BINDIR_NATIVE') + '/' + sys_multilib)
        d.setVar('KERNEL_CC', d.getVar('CCACHE') + sys_multilib + '-' + 'gcc' + d.getVar('HOST_CC_KERNEL_ARCH') + tc_options)
        d.setVar('KERNEL_LD', d.getVar('CCACHE') + sys_multilib + '-' + 'ld.bfd' + d.getVar('HOST_LD_KERNEL_ARCH') + tc_options)
        d.setVar('KERNEL_AR', d.getVar('CCACHE') + sys_multilib + '-' + 'ar' + d.getVar('HOST_AR_KERNEL_ARCH'))

    error_qa = d.getVar('ERROR_QA', True)
    if 'arch' in error_qa:
        d.setVar('ERROR_QA', error_qa.replace(' arch', ''))
}
