require binutils-fsl.inc

do_install_append () {
	rm -f ${D}/usr/lib/libbfd.la
}
