PRINC := "${@int(PRINC) + 1}"

python () {
    ma = d.getVar("DISTRO_FEATURES", True)
    arch = d.getVar("OVERRIDES", True)

    # the : after the arch is to skip the message on 64b
    if not "multiarch" in ma and "e6500:" in arch:
        raise bb.parse.SkipPackage("Building the kernel for this arch requires multiarch to be in DISTRO_FEATURES")

    promote_kernel = d.getVar('BUILD_64BIT_KERNEL')

    if promote_kernel == "1":
        d.setVar('KERNEL_CC_append', ' -m64')
        d.setVar('KERNEL_LD_append', ' -melf64ppc')

    error_qa = d.getVar('ERROR_QA', True)
    if 'arch' in error_qa:
        d.setVar('ERROR_QA', error_qa.replace(' arch', ''))
}

