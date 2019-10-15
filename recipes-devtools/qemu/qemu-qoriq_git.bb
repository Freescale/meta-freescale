FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

require recipes-devtools/qemu/qemu.inc

inherit ptest

RDEPENDS_${PN}-ptest = "bash make"
DEPENDS = "glib-2.0 zlib pixman dtc"

LIC_FILES_CHKSUM = "file://COPYING;md5=441c28d2cf86e15a37fa47e15a72fbac \
                    file://COPYING.LIB;endline=24;md5=c04def7ae38850e7d3ef548588159913"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/qemu;nobranch=1 \
           file://powerpc_rom.bin \
           file://run-ptest \
           "

SRCREV = "ca6417b6c3fa7a965d021c98d079a1895c973dbb"

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "(qoriq|imx)"

PROVIDES = "qemu"

python() {
    pkgs = d.getVar('PACKAGES').split()
    for p in pkgs:
        if 'qemu-qoriq' in p:
            d.appendVar("RPROVIDES_%s" % p, p.replace('qemu-qoriq', 'qemu'))
            d.appendVar("RCONFLICTS_%s" % p, p.replace('qemu-qoriq', 'qemu'))
            d.appendVar("RREPLACES_%s" % p, p.replace('qemu-qoriq', 'qemu'))
}

EXTRA_OECONF_append = " --python=python2.7"

PPC_OECONF = '--enable-fdt --enable-kvm --with-system-pixman --disable-werror'
EXTRA_OECONF_qoriq-arm64 = "--prefix=${prefix} --target-list=aarch64-softmmu --enable-fdt --enable-kvm --with-system-pixman --disable-werror"
EXTRA_OECONF_qoriq-arm = "--prefix=${prefix} --target-list=arm-softmmu --enable-fdt --enable-kvm --with-system-pixman --disable-werror"
EXTRA_OECONF_e5500-64b = "--prefix=${prefix} --target-list=ppc64-softmmu ${PPC_OECONF}"
EXTRA_OECONF_e6500-64b = "--prefix=${prefix} --target-list=ppc64-softmmu ${PPC_OECONF}"
EXTRA_OECONF_e6500 = "--prefix=${prefix} --target-list=ppc64-softmmu ${PPC_OECONF}"
EXTRA_OECONF_e5500 = "--prefix=${prefix} --target-list=ppc64-softmmu ${PPC_OECONF}"
EXTRA_OECONF_e500v2 = "--prefix=${prefix} --target-list=ppc-softmmu ${PPC_OECONF}"
EXTRA_OECONF_e500mc = "--prefix=${prefix} --target-list=ppc-softmmu ${PPC_OECONF}"

DISABLE_STATIC = ""

do_install_append() {
    # Prevent QA warnings about installed ${localstatedir}/run
    if [ -d ${D}${localstatedir}/run ]; then rmdir ${D}${localstatedir}/run; fi
    install -Dm 0755 ${WORKDIR}/powerpc_rom.bin ${D}${datadir}/qemu
}

do_compile_ptest() {
	make buildtest-TESTS
}

do_install_ptest() {
	cp -rL ${B}/tests ${D}${PTEST_PATH}
	find ${D}${PTEST_PATH}/tests -type f -name "*.[Sshcod]" | xargs -i rm -rf {}

	cp ${S}/tests/Makefile.include ${D}${PTEST_PATH}/tests
	# Don't check the file genreated by configure
	sed -i -e '/wildcard config-host.mak/d' \
	       -e '$ {/endif/d}' ${D}${PTEST_PATH}/tests/Makefile.include
}

INSANE_SKIP_${PN} += "already-stripped"
FILES_${PN} += "/usr/share/qemu/* /usr/var/*"

# FIXME: Avoid WARNING due missing patch for native/nativesdk
BBCLASSEXTEND = ""
