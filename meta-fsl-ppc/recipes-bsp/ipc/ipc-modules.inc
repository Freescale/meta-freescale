SUMMARY = "Linux IPC KERNEL MODULE "
DESCRIPTION = "DSP boot application and ipc test application"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=fa38cd73d71527dc6efb546474f64d10"

require ipc.inc

inherit  module

S = "${WORKDIR}/git"
python () {
        ma = d.getVar("DISTRO_FEATURES", True)
        arch = d.getVar("OVERRIDES", True)

        # the : after the arch is to skip the message on 64b
        if not "multiarch" in ma and ("e5500:" in arch or "e6500:" in arch):
                raise bb.parse.SkipPackage("Building the kernel for this arch requires multiarch to be in DISTRO_FEATURES")

        promote_kernel = d.getVar('BUILD_64BIT_KERNEL')

        if promote_kernel == "1":
                d.setVar('KERNEL_CC_append', ' -m64')
                d.setVar('KERNEL_LD_append', ' -melf64ppc')

        error_qa = d.getVar('ERROR_QA', True)
        if 'arch' in error_qa:
                d.setVar('ERROR_QA', error_qa.replace(' arch', ''))
}

do_compile_prepend () {
    cd ${S}/kernel
    case ${MACHINE} in
        bsc9132qds|bsc9131rdb) SOC=B913x;;
        b4860qds|b4420qds) SOC=B4860;;
    esac
}

INHIBIT_PACKAGE_STRIP = "1"

