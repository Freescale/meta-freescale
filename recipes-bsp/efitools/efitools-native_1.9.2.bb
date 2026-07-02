require efitools.inc

inherit native

EXTRA_OEMAKE:append = " \
    INCDIR_PREFIX='${STAGING_DIR_NATIVE}' \
    CRTPATH_PREFIX='${STAGING_DIR_NATIVE}' \
"

do_compile() {
    oe_runmake binaries
}

do_install() {
    oe_runmake install-binaries DESTDIR='${D}${base_prefix}'
}
