# The AGL distro (poky-agl) ships its own weston.ini, so drop the i.MX one.
do_install:append:poky-agl() {
    rm -f ${D}${sysconfdir}/xdg/weston/weston.ini
}
