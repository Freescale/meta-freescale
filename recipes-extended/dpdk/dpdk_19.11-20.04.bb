LICENSE = "BSD-3-Clause & LGPLv2.1 & GPLv2"
LIC_FILES_CHKSUM = "file://license/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://license/lgpl-2.1.txt;md5=4b54a1fd55a448865a0b32d41598759d \
                    file://license/bsd-3-clause.txt;md5=0f00d99239d922ffd13cabef83b33444"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/dpdk;nobranch=1 \
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

