FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append_e500v2 = " file://coreutils-fix-dd-segfault-for-e500v2.patch \
"
