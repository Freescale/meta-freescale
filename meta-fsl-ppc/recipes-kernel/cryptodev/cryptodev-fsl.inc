FILESEXTRAPATHS_prepend := "${THISDIR}/cryptodev-fsl:"

SRC_URI_qoriq-ppc = "git://github.com/nmav/cryptodev-linux.git \
    file://0001-add-support-for-composite-TLS-SHA1-AES-algorithm-off.patch \
    file://0002-use-function-local-storage-for-cipher-and-hmac-keys.patch \
    file://0003-Add-support-for-aead-keys-for-composite-algorithms.patch \
    file://0004-fix-scatterlist-size-for-in-place-operations.patch \
    file://0005-add-dst_len-parameter-to-be-provided-by-the-user.patch \
    file://0006-fix-build-error-on-some-targets.patch \
    file://0007-add-support-for-COMPAT_CIOCAUTHCRYPT-ioctl.patch \
    file://0008-fix-cipher-algorithm-memleaks.patch \
    file://0009-PKC-support-added-in-cryptodev-module.patch \
    file://0010-Compat-versions-of-PKC-IOCTLs.patch \
    file://0011-Asynchronous-interface-changes-in-cryptodev.patch \
    file://0012-ECC_KEYGEN-and-DLC_KEYGEN-supported-in-cryptodev-mod.patch \
    file://0013-RCU-stall-fixed-in-PKC-asynchronous-interface.patch \
    file://0014-use-static-allocation-for-keys-copied-from-userspace.patch \
    file://0015-Add-RSA-Key-generation-offloading.patch \
    file://0016-Fixed-compilation-error-of-openssl-with-fsl-cryptode.patch \
"
SRCREV_qoriq-ppc = "e8609a408d40c83ae8fc11bbe9a7b3847612669b"

S_qoriq-ppc = "${WORKDIR}/git"

