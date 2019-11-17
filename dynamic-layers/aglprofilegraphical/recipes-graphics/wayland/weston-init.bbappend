do_install_append() {
    rm -f ${D}${sysconfdir}/xdg/weston/weston.ini
}
