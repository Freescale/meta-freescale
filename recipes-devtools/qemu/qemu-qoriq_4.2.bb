BBCLASSEXTEND = ""

require qemu-qoriq.inc

COMPATIBLE_MACHINE = "(qoriq)"

DEPENDS += "bison-native glib-2.0 pixman zlib"

SRC_URI = "gitsm://github.com/nxp-qoriq/qemu;protocol=https;nobranch=1 \
           file://powerpc_rom.bin \
           file://run-ptest \
           file://0002-Add-subpackage-ptest-which-runs-all-unit-test-cases-.patch \
           "

SRCREV = "a46ddbbe661677dcfa342f00ab7ab71e5f6f1a09"

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
EXTRA_OECONF:append:class-target:mipsarcho32 = " ${@bb.utils.contains('BBEXTENDCURR', 'multilib', '--disable-capstone', '', d)}"

do_install_ptest() {
        cp -rL ${B}/tests ${D}${PTEST_PATH}
        find ${D}${PTEST_PATH}/tests -type f -name "*.[Sshcod]" | xargs -i rm -rf {}

        install -m 0644 ${S}/tests/Makefile.include ${D}${PTEST_PATH}/tests
        # Don't check the file genreated by configure
        sed -i -e '/wildcard config-host.mak/d' \
               -e '$ {/endif/d}' ${D}${PTEST_PATH}/tests/Makefile.include
        sed -i -e 's,${HOSTTOOLS_DIR}/python3,${bindir}/python3,' \
            ${D}/${PTEST_PATH}/tests/qemu-iotests/common.env
}

PACKAGECONFIG ??= "\
    fdt sdl kvm aio libusb vhost numa \
    ${@bb.utils.filter('DISTRO_FEATURES', 'alsa xen', d)} \
"

PACKAGECONFIG[xkbcommon] = ",,"
PACKAGECONFIG[libudev] = ",,"

DISABLE_STATIC = ""

