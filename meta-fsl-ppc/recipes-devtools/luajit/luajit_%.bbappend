FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append_qoriq-ppc = " file://ppc-fixplt.patch "

