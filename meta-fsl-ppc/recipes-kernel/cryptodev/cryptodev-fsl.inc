FILESEXTRAPATHS_prepend := "${THISDIR}/cryptodev-fsl:"

SRC_URI_qoriq-ppc = "git://github.com/cryptodev-linux/cryptodev-linux.git \
	file://0001-add-support-for-composite-TLS10-SHA1-AES-algorithm-o.patch \
	file://0002-add-support-for-COMPAT_CIOCAUTHCRYPT-ioctl.patch \
	file://0003-PKC-support-added-in-cryptodev-module.patch \
	file://0004-Compat-versions-of-PKC-IOCTLs.patch \
	file://0005-Asynchronous-interface-changes-in-cryptodev.patch \
	file://0006-ECC_KEYGEN-and-DLC_KEYGEN-supported-in-cryptodev-mod.patch \
	file://0007-RCU-stall-fixed-in-PKC-asynchronous-interface.patch \
	file://0008-Add-RSA-Key-generation-offloading.patch \
	file://0009-Fixed-compilation-error-of-openssl-with-fsl-cryptode.patch \
"
SRCREV_qoriq-ppc = "6aa62a2c320b04f55fdfe0ed015c3d9b48997239"

S_qoriq-ppc = "${WORKDIR}/git"

