require openssl-qoriq.inc

DISABLE_STATIC = ""
RRECOMMENDS_libcrypto += "cryptodev-module"
COMPATIBLE_MACHINE = "(qoriq)"

# For target side versions of openssl enable support for OCF Linux driver
# if they are available.
DEPENDS += "cryptodev-linux"

CFLAG += "-DHAVE_CRYPTODEV -DUSE_CRYPTODEV_DIGESTS"

LIC_FILES_CHKSUM = "file://LICENSE;md5=27ffa5d74bb5a337056c14b2ef93fbf6"

export DIRS = "crypto ssl apps engines"
export OE_LDFLAGS="${LDFLAGS}"

SRC_URI += "file://find.pl;subdir=openssl-${PV}/util/ \
            file://run-ptest \
            file://openssl-c_rehash.sh \
            file://configure-targets.patch \
            file://shared-libs.patch \
            file://oe-ldflags.patch \
            file://engines-install-in-libdir-ssl.patch \
            file://debian1.0.2/block_diginotar.patch \
            file://debian1.0.2/block_digicert_malaysia.patch \
            file://debian/ca.patch \
            file://debian/c_rehash-compat.patch \
            file://debian/debian-targets.patch \
            file://debian/man-dir.patch \
            file://debian/man-section.patch \
            file://debian/no-rpath.patch \
            file://debian/no-symbolic.patch \
            file://debian/pic.patch \
            file://debian1.0.2/version-script.patch \
            file://openssl_fix_for_x32.patch \
            file://fix-cipher-des-ede3-cfb1.patch \
            file://openssl-avoid-NULL-pointer-dereference-in-EVP_DigestInit_ex.patch \
            file://openssl-fix-des.pod-error.patch \
            file://Makefiles-ptest.patch \
            file://ptest-deps.patch \
            file://openssl-1.0.2a-x32-asm.patch \
            file://ptest_makefile_deps.patch  \
            file://configure-musl-target.patch \
            file://parallel.patch \
            file://CVE-2016-2177.patch \
            file://CVE-2016-2178.patch \
           "
SRC_URI += "file://0001-remove-double-initialization-of-cryptodev-engine.patch \
	file://0002-eng_cryptodev-add-support-for-TLS-algorithms-offload.patch \
	file://0003-cryptodev-fix-algorithm-registration.patch \
	file://0004-ECC-Support-header-for-Cryptodev-Engine.patch \
	file://0005-Initial-support-for-PKC-in-cryptodev-engine.patch \
	file://0006-Added-hwrng-dev-file-as-source-of-RNG.patch \
	file://0007-Asynchronous-interface-added-for-PKC-cryptodev-inter.patch \
	file://0008-Add-RSA-keygen-operation-and-support-gendsa-command-.patch \
	file://0009-RSA-Keygen-Fix.patch \
	file://0010-Removed-local-copy-of-curve_t-type.patch \
	file://0011-Modulus-parameter-is-not-populated-by-dhparams.patch \
	file://0012-SW-Backoff-mechanism-for-dsa-keygen.patch \
	file://0013-Fixed-DH-keygen-pair-generator.patch \
	file://0014-cryptodev-add-support-for-aes-gcm-algorithm-offloadi.patch \
	file://0015-eng_cryptodev-extend-TLS-offload-with-3des_cbc_hmac_.patch \
	file://0016-eng_cryptodev-add-support-for-TLSv1.1-record-offload.patch \
	file://0017-eng_cryptodev-add-support-for-TLSv1.2-record-offload.patch \
	file://0018-cryptodev-drop-redundant-function.patch \
	file://0019-cryptodev-do-not-zero-the-buffer-before-use.patch \
	file://0020-cryptodev-clean-up-code-layout.patch \
	file://0021-cryptodev-do-not-cache-file-descriptor-in-open.patch \
	file://0022-cryptodev-put_dev_crypto-should-be-an-int.patch \
	file://0023-cryptodev-simplify-cryptodev-pkc-support-code.patch \
	file://0024-cryptodev-clarify-code-remove-assignments-from-condi.patch \
	file://0025-cryptodev-clean-up-context-state-before-anything-els.patch \
	file://0026-cryptodev-remove-code-duplication-in-digest-operatio.patch \
	file://0027-cryptodev-put-all-digest-ioctls-into-a-single-functi.patch \
	file://0028-cryptodev-fix-debug-print-messages.patch \
	file://0029-cryptodev-use-CIOCHASH-ioctl-for-digest-operations.patch \
	file://0030-cryptodev-reduce-duplicated-efforts-for-searching-in.patch \
	file://0031-cryptodev-remove-not-used-local-variables.patch \
	file://0032-cryptodev-hide-not-used-variable-behind-ifndef.patch \
	file://0033-cryptodev-fix-function-declaration-typo.patch \
	file://0034-cryptodev-fix-incorrect-function-signature.patch \
	file://0035-cryptodev-fix-warnings-on-excess-elements-in-struct-.patch \
	file://0036-cryptodev-fix-free-on-error-path.patch \
	file://0037-cryptodev-fix-return-value-on-error.patch \
	file://0038-cryptodev-match-types-with-cryptodev.h.patch \
	file://0039-cryptodev-explicitly-discard-const-qualifier.patch \
	file://0040-cryptodev-replace-caddr_t-with-void.patch \
	file://0041-cryptodev-check-for-errors-inside-cryptodev_rsa_mod_.patch \
	file://0042-cryptodev-check-for-errors-inside-cryptodev_rsa_mod_.patch \
	file://0043-cryptodev-check-for-errors-inside-cryptodev_dh_compu.patch \
	file://0044-cryptodev-check-for-errors-inside-cryptodev_dh_compu.patch \
	file://0045-cryptodev-change-signature-for-conversion-functions.patch \
	file://0046-cryptodev-add-explicit-cast-for-known-BIGNUM-values.patch \
	file://0047-cryptodev-treat-all-build-warnings-as-errors.patch \
	file://0048-fix-maclen-is-used-uninitialized-warning-on-some-com.patch \
"

SRC_URI[md5sum] = "9392e65072ce4b614c1392eefc1f23d0"
SRC_URI[sha256sum] = "1d4007e53aad94a5b2002fe045ee7bb0b3d98f1a47f8b2bc851dcd1c74332919"

PACKAGES =+ "${PN}-engines"
FILES_${PN}-engines = "${libdir}/ssl/engines/*.so ${libdir}/engines"

# The crypto_use_bigint patch means that perl's bignum module needs to be
# installed, but some distributions (for example Fedora 23) don't ship it by
# default.  As the resulting error is very misleading check for bignum before
# building.
do_configure_prepend() {
	if ! perl -Mbigint -e true; then
		bbfatal "The perl module 'bignum' was not found but this is required to build openssl.  Please install this module (often packaged as perl-bignum) and re-run bitbake."
	fi
}
