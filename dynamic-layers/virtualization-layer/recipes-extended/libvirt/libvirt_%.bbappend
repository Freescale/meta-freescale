# Standard bbappend idiom: cannot carry an override, and FILESEXTRAPATHS is
# in BB_BASEHASH_IGNORE_VARS, so it cannot reach a task signature.
# nooelint: oelint.vars.noncoreoverride
FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI:append:qoriq-ppc = " file://qemu.conf"

PACKAGECONFIG:qoriq-ppc = "qemu yajl lxc test remote macvtap libvirtd netcf udev python"

do_install:append:qoriq-ppc() {
    install -m 0644 ${UNPACKDIR}/qemu.conf ${D}${sysconfdir}/libvirt/qemu.conf
}
