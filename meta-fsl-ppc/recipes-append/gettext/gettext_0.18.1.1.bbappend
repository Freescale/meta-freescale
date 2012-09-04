FILESEXTRAPATHS_prepend_fsl := "${THISDIR}/files:"

SRC_URI_fsl += "file://gettext.fix_testcase.patch"

PR_fsl .= "+${DISTRO}.0"
