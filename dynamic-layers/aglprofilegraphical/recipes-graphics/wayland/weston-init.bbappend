do_install:append() {
    rm -f ${D}${sysconfdir}/xdg/weston/weston.ini
}
