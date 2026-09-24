inherit features_check
REQUIRED_DISTRO_FEATURES:append:e6500 = " multiarch"

# BUILD_64BIT_KERNEL is this class's interface, not a machine override: any
# machine that sets it gets the promotion, and in-tree only e6500.inc does.
# KERNEL_CC, KERNEL_LD and KERNEL_AR are replaced only when the flag is set;
# expressing that declaratively means restating kernel.bbclass defaults here.
# nooelint: oelint.vars.noncoreoverride oelint.task.noanonpython
python () {
    promote_kernel = d.getVar('BUILD_64BIT_KERNEL', False)
    if promote_kernel == "1":
        sys_multilib = 'powerpc64' + d.getVar('TARGET_VENDOR', False) + 'mllib64-' + d.getVar('HOST_OS', False)
        tc_options = d.getVar('TOOLCHAIN_OPTIONS', False) + '/../lib64-' + d.getVar("MACHINE", False)
        d.setVar('DEPENDS:append', ' lib64-gcc-cross-powerpc64 lib64-libgcc')
        d.setVar('PATH:append', ':' + d.getVar('STAGING_BINDIR_NATIVE', False) + '/' + sys_multilib)
        # oe-core dropped the HOST_*_KERNEL_ARCH aliases of these; referencing
        # TARGET_* keeps the exact value they used to contribute.
        d.setVar('KERNEL_CC', d.getVar('CCACHE', False) + sys_multilib + '-' + 'gcc' + '${TARGET_CC_KERNEL_ARCH}' + tc_options)
        d.setVar('KERNEL_LD', d.getVar('CCACHE', False) + sys_multilib + '-' + 'ld.bfd' + '${TARGET_LD_KERNEL_ARCH}' + tc_options)
        d.setVar('KERNEL_AR', d.getVar('CCACHE', False) + sys_multilib + '-' + 'ar' + '${TARGET_AR_KERNEL_ARCH}')

        error_qa = (d.getVar('ERROR_QA') or '').split()
        if 'arch' in error_qa:
            d.setVar('ERROR_QA', ' '.join(flag for flag in error_qa if flag != 'arch'))
}
