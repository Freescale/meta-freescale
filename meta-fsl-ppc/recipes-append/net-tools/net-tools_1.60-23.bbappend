PR_append_fsl = "+${DISTRO}.0"

inherit update-alternatives

ALTERNATIVE_NAME_fsl = "ifconfig"
ALTERNATIVE_LINK_fsl = "${base_sbindir}/ifconfig"
ALTERNATIVE_PATH_fsl = "${base_sbindir}/ifconfig.net-tools"
ALTERNATIVE_PRIORITY_fsl = "200"

