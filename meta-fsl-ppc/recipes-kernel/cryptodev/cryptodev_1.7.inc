HOMEPAGE = "http://cryptodev-linux.org/"

RCONFLICTS_${PN} = "ocf-linux"
RREPLACES_${PN} = "ocf-linux"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "http://download.gna.org/cryptodev-linux/cryptodev-linux-${PV}.tar.gz"
SRC_URI[md5sum] = "0b63b3481cf2c90386b35f057481d36b"
SRC_URI[sha256sum] = "41880533b53de4d7b3f054e230f576988dafb8eed7bef5ebcf6422bb2e3a3b25"

# Upstream hotfixes and yocto specific patches
SRC_URI_append = " \
file://0001-fix-compilation-against-linux-3.19.patch \
file://0002-tests-Makefile-fix-arg-passing-to-CC-in-implicit-rul.patch \
file://0003-Disable-installing-header-file-provided-by-another-p.patch \
file://0004-Add-the-compile-and-install-rules-for-cryptodev-test.patch \
"
# SDK patches
SRC_URI_append = " file://0001-add-support-for-composite-TLS10-SHA1-AES-algorithm-o.patch \
file://0002-add-support-for-COMPAT_CIOCAUTHCRYPT-ioctl.patch \
file://0003-PKC-support-added-in-cryptodev-module.patch \
file://0004-Compat-versions-of-PKC-IOCTLs.patch \
file://0005-Asynchronous-interface-changes-in-cryptodev.patch \
file://0006-ECC_KEYGEN-and-DLC_KEYGEN-supported-in-cryptodev-mod.patch \
file://0007-RCU-stall-fixed-in-PKC-asynchronous-interface.patch \
file://0008-Add-RSA-Key-generation-offloading.patch \
file://0009-Fixed-compilation-error-of-openssl-with-fsl-cryptode.patch \
file://0010-add-support-for-composite-TLS10-SHA1-3DES-algorithm-.patch \
file://0011-add-support-for-TLSv1.1-record-offload.patch \
file://0012-add-support-for-TLSv1.2-record-offload.patch \
file://0013-clean-up-code-layout.patch \
file://0014-remove-redundant-data-copy-for-pkc-operations.patch \
file://0015-fix-pkc-request-deallocation.patch \
file://0016-add-basic-detection-of-asym-features.patch \
file://0017-remove-dead-code.patch \
file://0018-fix-compat-warnings.patch \
file://0019-fix-size_t-print-format.patch \
file://0020-fix-uninitialized-variable-compiler-warning.patch \
"

# NOTE: remove this patch and all traces of DISTRO_FEATURE c29x_pkc
# if pkc-host does not need customized cryptodev patches anymore
SRC_URI_append = "${@base_contains('DISTRO_FEATURES', 'c29x_pkc', ' file://0001-don-t-advertise-RSA-keygen.patch', '', d)}"

S = "${WORKDIR}/cryptodev-linux-${PV}"
