FILESEXTRAPATHS_prepend := "${THISDIR}/openssl-fsl:"

RDEPENDS_${PN}_class-target += "cryptodev-module"

# base package is taken from Freescale repository
SRCBRANCH = "OpenSSL_1_0_1-stable"
SRC_URI = "git://git.openssl.org/openssl.git;branch=${SRCBRANCH} \
	file://0001-remove-double-initialization-of-cryptodev-engine.patch \
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
"
SRCREV = "2b456034457b58454aae3998a2765b6a5b9bc837"

SRC_URI += "file://configure-targets.patch \
            file://shared-libs.patch \
            file://oe-ldflags.patch \
            file://engines-install-in-libdir-ssl.patch \
            file://openssl-fix-link.patch \
            file://debian/version-script.patch \
            file://debian/pic.patch \
            file://debian/c_rehash-compat.patch \
            file://debian/ca.patch \
            file://debian/make-targets.patch \
            file://debian/no-rpath.patch \
            file://debian/man-dir.patch \
            file://debian/man-section.patch \
            file://debian/no-symbolic.patch \
            file://debian/debian-targets.patch \
            file://openssl_fix_for_x32.patch \
            file://fix-cipher-des-ede3-cfb1.patch \
            file://openssl-avoid-NULL-pointer-dereference-in-EVP_DigestInit_ex.patch \
            file://openssl-avoid-NULL-pointer-dereference-in-dh_pub_encode.patch \
            file://initial-aarch64-bits.patch \
            file://find.pl \
            file://openssl-fix-des.pod-error.patch \
           "
S = "${WORKDIR}/git"

# Digest offloading through cryptodev is not recommended because of the
# performance penalty of the Openssl engine interface. Openssl generates a huge
# number of calls to digest functions for even a small amount of work data.
# For example there are 70 calls to cipher code and over 10000 to digest code
# when downloading only 10 files of 700 bytes each.
# Do not build OpenSSL with cryptodev digest support until engine digest
# interface gets some rework:
CFLAG := "${@'${CFLAG}'.replace('-DUSE_CRYPTODEV_DIGESTS', '')}"
