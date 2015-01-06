require u-boot-ls1.inc

SRC_URI = "git://git.freescale.com/layerscape/ls1021a/u-boot.git;branch=LS1-dev"
SRCREV = "50d684801cd05ed6b77d52d1ca9ed00fefeac469"

RDEPENDS += "tcl-native"

inherit native

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    install -d ${D}/${bindir}
    install -m 755 ${S}/byte_swap.tcl ${D}/${bindir}
}
