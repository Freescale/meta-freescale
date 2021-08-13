FILESEXTRAPATHS:append := "${THISDIR}/${PN}-qoriq:"

SRC_URI:append:qoriq = " \
	file://0001-eng_devcrypto-add-support-for-TLS-algorithms-offload.patch \
	file://0002-eng_devcrypto-add-support-for-TLS1.2-algorithms-offl.patch \
"

PACKAGECONFIG:append:qoriq = " cryptodev-linux"
