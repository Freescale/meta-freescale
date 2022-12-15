BBCLASSEXTEND = ""

require qemu.inc

COMPATIBLE_MACHINE = "(qoriq)"

DEPENDS = "glib-2.0 zlib pixman bison-native"

LIC_FILES_CHKSUM = "file://COPYING;md5=441c28d2cf86e15a37fa47e15a72fbac \
                    file://COPYING.LIB;endline=24;md5=8c5efda6cf1e1b03dcfd0e6c0d271c7f"

SRC_URI = "gitsm://github.com/nxp-qoriq/qemu;protocol=https;nobranch=1 \
           file://powerpc_rom.bin \
           file://run-ptest \
           file://0002-Add-subpackage-ptest-which-runs-all-unit-test-cases-.patch \
           "

SRCREV = "14fda5a42df6c72e890d6a97ff88c5852172604b"

S = "${WORKDIR}/git"

python() {
    d.appendVar('PROVIDES', ' ' + d.getVar('BPN').replace('-qoriq', ''))
    pkgs = d.getVar('PACKAGES').split()
    for p in pkgs:
        if '-qoriq' in p:
            d.appendVar('RPROVIDES:' + p, ' ' + p.replace('-qoriq', ''))
            d.appendVar('RCONFLICTS:' + p, ' ' + p.replace('-qoriq', ''))
            d.appendVar('RREPLACES:' + p, ' ' + p.replace('-qoriq', ''))
}

RDEPENDS:${PN}:class-target += "bash"

EXTRA_OECONF:append:class-target = " --target-list=${@get_qemu_target_list(d)}"
EXTRA_OECONF:append:class-target:mipsarcho32 = "${@bb.utils.contains('BBEXTENDCURR', 'multilib', ' --disable-capstone', '', d)}"
EXTRA_OECONF:append:class-nativesdk = " --target-list=${@get_qemu_target_list(d)}"

do_install:append:class-nativesdk() {
     ${@bb.utils.contains('PACKAGECONFIG', 'gtk+', 'make_qemu_wrapper', '', d)}
}

do_install_ptest() {
        cp -rL ${B}/tests ${D}${PTEST_PATH}
        find ${D}${PTEST_PATH}/tests -type f -name "*.[Sshcod]" | xargs -i rm -rf {}

        cp ${S}/tests/Makefile.include ${D}${PTEST_PATH}/tests
        # Don't check the file genreated by configure
        sed -i -e '/wildcard config-host.mak/d' \
               -e '$ {/endif/d}' ${D}${PTEST_PATH}/tests/Makefile.include
        sed -i -e 's,${HOSTTOOLS_DIR}/python3,${bindir}/python3,' \
            ${D}/${PTEST_PATH}/tests/qemu-iotests/common.env
}

PACKAGECONFIG ??= " \
    fdt sdl kvm aio libusb vhost numa \
    ${@bb.utils.filter('DISTRO_FEATURES', 'alsa xen', d)} \
"
PACKAGECONFIG:class-nativesdk ??= "fdt sdl kvm"

PACKAGECONFIG[xkbcommon] = ",,"
PACKAGECONFIG[libudev] = ",,"

DISABLE_STATIC = ""

