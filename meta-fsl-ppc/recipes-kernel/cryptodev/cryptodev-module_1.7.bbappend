require recipes-kernel/cryptodev/cryptodev-fsl.inc

inherit qoriq_build_64bit_kernel

do_install_append_qoriq-ppc () {
    rm -fr ${D}/usr
}

# Currently pkc-host does not support RSA_KEYGEN, remove this
# if it is fixed.
SRC_URI_append_qoriq-ppc = "${@base_contains('DISTRO_FEATURES', 'c29x_pkc', ' file://0001-don-t-advertise-RSA-keygen.patch', '', d)}"

