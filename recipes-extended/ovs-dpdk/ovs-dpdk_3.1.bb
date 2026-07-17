SUMMARY = "Open vSwitch accelerated with DPDK"
DESCRIPTION = "Open vSwitch (multilayer virtual switch) built on top of DPDK for \
               fast userspace packet forwarding on NXP QorIQ platforms."
HOMEPAGE = "https://github.com/nxp-qoriq/ovs-dpdk"
SECTION = "networking"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ce5d23a6429dff345518758f13aaeab"

DEPENDS = "autoconf-native automake-native coreutils-native dpdk python3-six-native"

SRC_URI = "git://github.com/nxp-qoriq/ovs-dpdk;protocol=https;nobranch=1"
SRCREV = "7b4861e1f77bbea5ff9952717b66362fdecbca4d"

inherit python3native pkgconfig

do_configure() {
    export SYSROOT_DPDK=${PKG_CONFIG_SYSROOT_DIR}
    ${S}/boot.sh
    ${S}/configure --host aarch64-fsl-linux --with-dpdk=static --with-openssl=${SYSROOT_DPDK}/usr CFLAGS="-g -Wno-cast-align -Ofast"
}

do_install:append() {
    install -d ${D}${bindir}/ovs-dpdk
    install -m 0755 ${S}/ovsdb/ovsdb-tool ${D}${bindir}/ovs-dpdk
    install -m 0755 ${S}/ovsdb/ovsdb-server ${D}${bindir}/ovs-dpdk
    install -m 0755 ${S}/ovsdb/ovsdb-client ${D}${bindir}/ovs-dpdk
    install -m 0755 ${S}/vswitchd/vswitch.ovsschema ${D}${bindir}/ovs-dpdk
    install -m 0755 ${S}/vswitchd/ovs-vswitchd ${D}${bindir}/ovs-dpdk
    install -m 0755 ${S}/utilities/ovs-vsctl ${D}${bindir}/ovs-dpdk
    install -m 0755 ${S}/utilities/ovs-ofctl ${D}${bindir}/ovs-dpdk
}

# The upstream build hardcodes cross host/CFLAGS paths, so the produced
# binaries embed build paths that cannot be scrubbed; skip the buildpaths QA
# check rather than fail packaging.
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN}-dbg += "buildpaths"
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN} += "buildpaths"

ALLOW_EMPTY:${PN} = "1"
INHIBIT_PACKAGE_STRIP = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "bash libcrypto libssl python3"

COMPATIBLE_MACHINE = "(qoriq-arm64)"
