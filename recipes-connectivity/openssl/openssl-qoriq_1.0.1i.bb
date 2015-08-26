require openssl-qoriq.inc

# For target side versions of openssl enable support for cryptodev Linux driver
# if they are available.
DEPENDS_class-target += "cryptodev-linux"
CFLAG_class-target += "-DHAVE_CRYPTODEV -DUSE_CRYPTODEV_DIGESTS"

LIC_FILES_CHKSUM = "file://LICENSE;md5=f9a8f968107345e0b75aa8c2ecaa7ec8"

export DIRS = "crypto ssl apps engines"
export OE_LDFLAGS="${LDFLAGS}"

PROVIDES = "openssl"

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

SRC_URI_append_class-target = "\
	file://qoriq/0001-remove-double-initialization-of-cryptodev-engine.patch \
	file://qoriq/0002-eng_cryptodev-add-support-for-TLS-algorithms-offload.patch \
	file://qoriq/0003-cryptodev-fix-algorithm-registration.patch \
	file://qoriq/0004-linux-pcc-make-it-more-robust-and-recognize-KERNEL_B.patch \
	file://qoriq/0005-ECC-Support-header-for-Cryptodev-Engine.patch \
	file://qoriq/0006-Fixed-private-key-support-for-DH.patch \
	file://qoriq/0007-Fixed-private-key-support-for-DH.patch \
	file://qoriq/0008-Initial-support-for-PKC-in-cryptodev-engine.patch \
	file://qoriq/0009-Added-hwrng-dev-file-as-source-of-RNG.patch \
	file://qoriq/0010-Asynchronous-interface-added-for-PKC-cryptodev-inter.patch \
	file://qoriq/0011-Add-RSA-keygen-operation-and-support-gendsa-command-.patch \
	file://qoriq/0012-RSA-Keygen-Fix.patch \
	file://qoriq/0013-Removed-local-copy-of-curve_t-type.patch \
	file://qoriq/0014-Modulus-parameter-is-not-populated-by-dhparams.patch \
	file://qoriq/0015-SW-Backoff-mechanism-for-dsa-keygen.patch \
	file://qoriq/0016-Fixed-DH-keygen-pair-generator.patch \
	file://qoriq/0017-cryptodev-add-support-for-aes-gcm-algorithm-offloadi.patch \
	file://qoriq/0018-eng_cryptodev-extend-TLS-offload-with-3des_cbc_hmac_.patch \
	file://qoriq/0019-eng_cryptodev-add-support-for-TLSv1.1-record-offload.patch \
	file://qoriq/0020-eng_cryptodev-add-support-for-TLSv1.2-record-offload.patch \
	file://qoriq/0021-cryptodev-drop-redundant-function.patch \
	file://qoriq/0022-cryptodev-do-not-zero-the-buffer-before-use.patch \
	file://qoriq/0023-cryptodev-clean-up-code-layout.patch \
	file://qoriq/0024-cryptodev-do-not-cache-file-descriptor-in-open.patch \
	file://qoriq/0025-cryptodev-put_dev_crypto-should-be-an-int.patch \
	file://qoriq/0026-cryptodev-simplify-cryptodev-pkc-support-code.patch \
"

SRC_URI[md5sum] = "c8dc151a671b9b92ff3e4c118b174972"
SRC_URI[sha256sum] = "3c179f46ca77069a6a0bac70212a9b3b838b2f66129cb52d568837fc79d8fcc7"

PACKAGES =+ " \
        ${PN}-engines-dbg \
        ${PN}-engines \
"

FILES_${PN}-engines = "${libdir}/ssl/engines/*.so ${libdir}/engines"
FILES_${PN}-engines-dbg = "${libdir}/engines/.debug ${libdir}/ssl/engines/.debug"

PARALLEL_MAKE = ""
PARALLEL_MAKEINST = ""

# Digest offloading through cryptodev is not recommended because of the
# performance penalty of the Openssl engine interface. Openssl generates a huge
# number of calls to digest functions for even a small amount of work data.
# For example there are 70 calls to cipher code and over 10000 to digest code
# when downloading only 10 files of 700 bytes each.
# Do not build OpenSSL with cryptodev digest support until engine digest
# interface gets some rework:
CFLAG_class-target := "${@'${CFLAG}'.replace('-DUSE_CRYPTODEV_DIGESTS', '')}"

do_configure_prepend() {
  cp ${WORKDIR}/find.pl ${S}/util/find.pl
}


RDEPENDS_${PN}_class-target += "cryptodev-module"

COMPATIBLE_MACHINE = "(qoriq)"
