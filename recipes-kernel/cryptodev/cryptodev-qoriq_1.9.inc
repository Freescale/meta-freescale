HOMEPAGE = "http://cryptodev-linux.org/"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

python() {
    pkgs = d.getVar('PACKAGES', True).split()
    for p in pkgs:
        if 'cryptodev-qoriq' in p:
            d.appendVar("RPROVIDES_%s" % p, p.replace('cryptodev-qoriq', 'cryptodev'))
            d.appendVar("RCONFLICTS_%s" % p, p.replace('cryptodev-qoriq', 'cryptodev'))
            d.appendVar("RREPLACES_%s" % p, p.replace('cryptodev-qoriq', 'cryptodev'))
}

FILESEXTRAPATHS_prepend := "${THISDIR}/sdk_patches:"
FILESEXTRAPATHS_prepend := "${THISDIR}/yocto_patches:"

SRC_URI = "http://nwl.cc/pub/cryptodev-linux/cryptodev-linux-${PV}.tar.gz"

SRC_URI[md5sum] = "cb4e0ed9e5937716c7c8a7be84895b6d"
SRC_URI[sha256sum] = "9f4c0b49b30e267d776f79455d09c70cc9c12c86eee400a0d0a0cd1d8e467950"

# SDK patches
SRC_URI_append = " file://0001-refactoring-split-big-function-to-simplify-maintaina.patch \
 file://0002-refactoring-relocate-code-to-simplify-later-patches.patch  \
 file://0003-convert-to-new-AEAD-interface-in-kernels-v4.2.patch \
 file://0004-fix-type-of-returned-value.patch \
 file://0005-remove-unnecessary-header-inclusion.patch \
 file://0006-move-structure-definition-to-cryptodev_int.h.patch \
 file://0007-add-support-for-RSA-public-and-private-key-operation.patch \
 file://0008-check-session-flags-early-to-avoid-incorrect-failure.patch \
 file://0009-add-support-for-composite-TLS10-SHA1-AES-algorithm-o.patch \
"
#SRC_URI_append = " file://0003-update-the-install-path-for-cryptodev-tests.patch"

# NOTE: remove this patch and all traces of DISTRO_FEATURE c29x_pkc
# if pkc-host does not need customized cryptodev patches anymore
#SRC_URI_append = "${@bb.utils.contains('DISTRO_FEATURES', 'c29x_pkc', ' file://0001-don-t-advertise-RSA-keygen.patch', '', d)}"

S = "${WORKDIR}/cryptodev-linux-${PV}"

CLEANBROKEN = "1"
