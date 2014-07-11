require recipes-kernel/cryptodev/cryptodev-fsl.inc

inherit qoriq_build_64bit_kernel

do_install_append_qoriq-ppc () {
    rm -fr ${D}/usr
}

