inherit distro_features_check

REQUIRED_DISTRO_FEATURES_append_e6500 ?= "multiarch"

python () {

        promote_kernel = d.getVar('BUILD_64BIT_KERNEL')

        if promote_kernel == "1":
                d.appendVar('KERNEL_CC', ' -m64')
                d.appendVar('KERNEL_LD', ' -melf64ppc')

        
        error_qa = d.getVar('ERROR_QA', True)
        if 'arch' in error_qa:
            d.setVar('ERROR_QA', error_qa.replace(' arch', ''))

}
