FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_mx6 = " file://Replace-glWindowPos2iARB-calls-with-glWindowPos2i.patch"

