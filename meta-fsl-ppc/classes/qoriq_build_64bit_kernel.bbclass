inherit distro_features_check
REQUIRED_DISTRO_FEATURES_e6500 += "multiarch"

python () {
    pkgarch = d.getVar("TUNE_PKGARCH", True)
    if not "ppce6500" == pkgarch:
        return

    promote_kernel = d.getVar('BUILD_64BIT_KERNEL')
    if promote_kernel == "1":
        d.setVar('KERNEL_CC_append', ' -m64')
        d.setVar('KERNEL_LD_append', ' -melf64ppc')

    error_qa = d.getVar('ERROR_QA', True)
    if 'arch' in error_qa:
        d.setVar('ERROR_QA', error_qa.replace(' arch', ''))
}

