DESCRIPTION = "Data Plane Development Kit"
HOMEPAGE = "http://dpdk.org"
LICENSE = "BSD-3-Clause & LGPL-2.1-only & GPL-2.0-only"
LIC_FILES_CHKSUM = "file://license/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://license/lgpl-2.1.txt;md5=4b54a1fd55a448865a0b32d41598759d \
                    file://license/bsd-3-clause.txt;md5=0f00d99239d922ffd13cabef83b33444"

DEPENDS = "python3-pyelftools-native"

DPDK_SRC ?= "git://github.com/nxp-qoriq/dpdk;protocol=https"
SRC_URI = "${DPDK_SRC};nobranch=1 \
          file://0001-meson.build-march-and-mcpu-already-passed-by-Yocto.patch"
SRCREV = "e09ece7ebcda131ee3d5a45999ad3396ee2092ae"

inherit meson pkgconfig

PACKAGECONFIG ??= "openssl examples"

PACKAGECONFIG[afxdp] = ",,libbpf"
PACKAGECONFIG[examples] = "-Denable_examples_bin_install=true -Dexamples=${DPDK_EXAMPLES},-Denable_examples_bin_install=false"
PACKAGECONFIG[libvirt] = ",,libvirt"
PACKAGECONFIG[openssl] = ",,openssl"

DPDK_EXAMPLES ?= "l2fwd,l3fwd,l2fwd-crypto,ipsec-secgw,ip_fragmentation,ip_reassembly,qdma_demo,timer,multi_process/simple_mp"
DPDK_APPS ?= "pdump,test-pmd,proc-info,test-crypto-perf"

# kernel module is provide by dpdk-module recipe, so disable here
EXTRA_OEMESON = " \
        -Denable_kmods=false \
        -Doptimization=3 \
        --cross-file ${S}/config/arm/arm64_poky_linux_gcc \
        -Denable_driver_sdk=true \
        ${@bb.utils.contains('DISTRO_FEATURES', 'vpp', '-Dc_args="-Ofast -fPIC -ftls-model=local-dynamic"', '', d)} \
        -Denable_examples_source_install=false \
        -Ddrivers_install_subdir= \
        -Denable_apps=${DPDK_APPS} \
"

do_install:append(){
    install -d ${D}/${sysconfdir}/dpdk
    cp -rf ${S}/nxp/* ${D}/${sysconfdir}/dpdk
}

RDEPENDS:${PN} += "bash pciutils python3-core python3-pyelftools"

COMPATIBLE_MACHINE = "(qoriq-arm64)"

CVE_PRODUCT = "data_plane_development_kit"
