PACKAGECONFIG_qoriq-ppc = "qemu yajl lxc test remote macvtap libvirtd netcf udev python"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI_append_qoriq-ppc = " file://qemu.conf"

do_install_append_qoriq-ppc() {
	install -m 0644 ${WORKDIR}/qemu.conf ${D}${sysconfdir}/libvirt/qemu.conf
}

