require u-boot-ls1.inc

RDEPENDS += "tcl-native"

inherit native

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    install -d ${D}/${bindir}
    install -m 755 ${S}/byte_swap.tcl ${D}/${bindir}
}
