LIC_FILES_CHKSUM = "file://license/README;md5=3383def2d4c82237df281174e981a492"

SRC_URI = "git://github.com/nxp-qoriq/dpdk;protocol=https;nobranch=1 \
           file://add-RTE_KERNELDIR_OUT-to-split-kernel-bu.patch \
           file://0001-add-Wno-cast-function-type.patch \
           file://0001-Add-RTE_KERNELDIR_OUT.patch \
           file://0004-update-WERROR_FLAGS.patch \
	   file://0001-examples-ipsec-gw-fix-gcc-10-maybe-uninitialized-war.patch \
"
SRCREV = "4110a5fed09fa034963cfc246a6285911ecbd540"

include dpdk.inc


do_install_append () {
    # Remove the unneeded dir
    rm -rf ${D}/${datadir}/${RTE_TARGET}/app
}

