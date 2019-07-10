include dpdk.inc


do_install_append () {
    # Remove the unneeded dir
    rm -rf ${D}/${datadir}/${RTE_TARGET}/app
}

