FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append_qoriq-ppc = " \
    file://skip-rules-for-mmc-rpmb-partition.patch \
"

