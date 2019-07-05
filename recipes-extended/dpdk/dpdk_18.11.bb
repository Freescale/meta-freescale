include dpdk.inc

do_install_append () {
    # Remove the unneeded dir
    rm -rf ${D}/${INSTALL_PATH}/${RTE_TARGET}/app
}

