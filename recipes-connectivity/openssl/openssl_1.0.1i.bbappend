FILESEXTRAPATHS_prepend := "${THISDIR}/openssl-fsl:"

RDEPENDS_${PN}_class-target += "cryptodev-module"

SRC_URI_append_class-target = " file://0001-remove-double-initialization-of-cryptodev-engine.patch \
	file://0002-eng_cryptodev-add-support-for-TLS-algorithms-offload.patch \
	file://0003-cryptodev-fix-algorithm-registration.patch \
	file://0004-linux-pcc-make-it-more-robust-and-recognize-KERNEL_B.patch \
	file://0005-ECC-Support-header-for-Cryptodev-Engine.patch \
	file://0006-Fixed-private-key-support-for-DH.patch \
	file://0007-Fixed-private-key-support-for-DH.patch \
	file://0008-Initial-support-for-PKC-in-cryptodev-engine.patch \
	file://0009-Added-hwrng-dev-file-as-source-of-RNG.patch \
	file://0010-Asynchronous-interface-added-for-PKC-cryptodev-inter.patch \
	file://0011-Add-RSA-keygen-operation-and-support-gendsa-command-.patch \
	file://0012-RSA-Keygen-Fix.patch \
	file://0013-Removed-local-copy-of-curve_t-type.patch \
	file://0014-Modulus-parameter-is-not-populated-by-dhparams.patch \
	file://0015-SW-Backoff-mechanism-for-dsa-keygen.patch \
	file://0016-Fixed-DH-keygen-pair-generator.patch \
	file://0017-cryptodev-add-support-for-aes-gcm-algorithm-offloadi.patch \
	file://0018-eng_cryptodev-extend-TLS-offload-with-3des_cbc_hmac_.patch \
	file://0019-eng_cryptodev-add-support-for-TLSv1.1-record-offload.patch \
	file://0020-eng_cryptodev-add-support-for-TLSv1.2-record-offload.patch \
	file://0021-cryptodev-drop-redundant-function.patch \
	file://0022-cryptodev-do-not-zero-the-buffer-before-use.patch \
	file://0023-cryptodev-clean-up-code-layout.patch \
	file://0024-cryptodev-do-not-cache-file-descriptor-in-open.patch \
	file://0025-cryptodev-put_dev_crypto-should-be-an-int.patch \
	file://0026-cryptodev-simplify-cryptodev-pkc-support-code.patch \
"

# Digest offloading through cryptodev is not recommended because of the
# performance penalty of the Openssl engine interface. Openssl generates a huge
# number of calls to digest functions for even a small amount of work data.
# For example there are 70 calls to cipher code and over 10000 to digest code
# when downloading only 10 files of 700 bytes each.
# Do not build OpenSSL with cryptodev digest support until engine digest
# interface gets some rework:
CFLAG_class-target := "${@'${CFLAG}'.replace('-DUSE_CRYPTODEV_DIGESTS', '')}"
