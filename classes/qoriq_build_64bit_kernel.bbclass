inherit features_check
REQUIRED_DISTRO_FEATURES:e6500 += "multiarch"

python () {
    promote_kernel = d.getVar('BUILD_64BIT_KERNEL', False)
    if promote_kernel == "1":
        sys_multilib = 'powerpc64' + d.getVar('TARGET_VENDOR', False) + 'mllib64-' + d.getVar('HOST_OS', False)
        tc_options = d.getVar('TOOLCHAIN_OPTIONS', False) + '/../lib64-' + d.getVar("MACHINE", False)
        d.setVar('DEPENDS:append', ' lib64-gcc-cross-powerpc64 lib64-libgcc')
        d.setVar('PATH:append', ':' + d.getVar('STAGING_BINDIR_NATIVE', False) + '/' + sys_multilib)
        d.setVar('KERNEL_CC', d.getVar('CCACHE', False) + sys_multilib + '-' + 'gcc' + d.getVar('HOST_CC_KERNEL_ARCH', False) + tc_options)
        d.setVar('KERNEL_LD', d.getVar('CCACHE', False) + sys_multilib + '-' + 'ld.bfd' + d.getVar('HOST_LD_KERNEL_ARCH', False) + tc_options)
        d.setVar('KERNEL_AR', d.getVar('CCACHE', False) + sys_multilib + '-' + 'ar' + d.getVar('HOST_AR_KERNEL_ARCH', False))

    error_qa = d.getVar('ERROR_QA')
    if 'arch' in error_qa:
        d.setVar('ERROR_QA', error_qa.replace(' arch', ''))
}
