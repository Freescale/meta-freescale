FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:qoriq-ppc = " file://ppc-fixplt.patch "

