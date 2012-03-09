FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://gettext.fix_testcase.patch"

PR .= "+${DISTRO}.0"
