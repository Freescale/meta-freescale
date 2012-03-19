EXTRA_OECONF_append_fsl = "\
    --enable-dpd \
    --enable-ipv6 \
    --enable-natt \
"

PR .= "+${DISTRO}.0"
