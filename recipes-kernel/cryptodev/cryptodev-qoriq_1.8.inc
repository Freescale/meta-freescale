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

SRC_URI = "http://download.gna.org/cryptodev-linux/cryptodev-linux-${PV}.tar.gz"

SRC_URI[md5sum] = "02644cc4cd02301e0b503a332eb2f0b5"
SRC_URI[sha256sum] = "67fabde9fb67b286a96c4f45b594b0eccd0f761b495705c18f2ae9461b831376"

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
file://0021-check-return-codes-for-copy-to-from-user-functions.patch \
file://0022-fix-double-free-pkc-req-on-error.patch \
file://0023-remove-MAY_BACKLOG-flag-from-requests.patch \
file://0024-fix-COMPAT_CIOCKEY-ioctl-command-number.patch \
file://0025-fix-benchmarks-linking.patch \
file://0026-fix-Makefile-to-allow-parallel-make-with-j-option.patch \
file://0027-use-Linux-kernel-conventions-for-Makefile-variables.patch \
file://0028-for-consistency-use-.-instead-of-.-in-makefiles.patch \
file://0029-fix-clean-up-on-error-path-for-crypto_create_session.patch \
file://0030-remove-code-duplication-in-cryptodev_hash_init.patch \
file://0031-fix-comment-typo.patch \
file://0032-avoid-calls-to-kmalloc-on-hotpaths.patch \
file://0033-avoid-unnecessary-checks-for-hash-clean-up.patch \
file://0034-extend-API-with-CIOCHASH-to-support-direct-hash-oper.patch \
file://0035-use-directly-crypto-API-digest-operation-for-CIOCHAS.patch \
file://0036-add-compat-for-CIOCHASH-operation.patch \
file://0037-rewrite-sha_speed.c-to-reduce-code-duplication.patch \
file://0038-extend-sha_speed.c-to-test-CIOCHASH.patch \
file://0039-fix-memory-leaks-on-error-path-for-CIOCHASH.patch \
file://0040-fix-structure-init-in-sha_speed-test.patch \
file://0041-add-separate-target-for-building-tests.patch \
file://0042-fix-destination-for-staged-installs.patch \
file://0043-add-install-target-for-tests.patch \
"

# NOTE: remove this patch and all traces of DISTRO_FEATURE c29x_pkc
# if pkc-host does not need customized cryptodev patches anymore
SRC_URI_append = "${@bb.utils.contains('DISTRO_FEATURES', 'c29x_pkc', ' file://0001-don-t-advertise-RSA-keygen.patch', '', d)}"

S = "${WORKDIR}/cryptodev-linux-${PV}"

CLEANBROKEN = "1"
