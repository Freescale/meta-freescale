
do_install_append() {
        echo "ll:1:respawn:${base_sbindir}/getty 115200 ttyS0 ">> ${D}${sysconfdir}/inittab
	echo "l2:1:respawn:${base_sbindir}/getty 115200 ttyEHV0">>  ${D}${sysconfdir}/inittab

}
PR .= "+${DISTRO}.0"

