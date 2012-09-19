FILESEXTRAPATHS_prepend_fsl := "${THISDIR}/files:"

PR_fsl .= "+${DISTRO}.1"

SRC_URI_append_fsl = " file://auto-detect-hostname.patch;patchdir=../"
