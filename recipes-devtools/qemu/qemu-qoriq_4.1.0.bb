BBCLASSEXTEND = ""

require recipes-devtools/qemu/qemu.inc

COMPATIBLE_MACHINE = "(qoriq)"

DEPENDS = "glib-2.0 zlib pixman bison-native"

LIC_FILES_CHKSUM = "file://COPYING;md5=441c28d2cf86e15a37fa47e15a72fbac \
                    file://COPYING.LIB;endline=24;md5=8c5efda6cf1e1b03dcfd0e6c0d271c7f"

SRC_URI = "gitsm://github.com/nxp-qoriq/qemu;protocol=https;nobranch=1 \
           file://powerpc_rom.bin \
           file://run-ptest \
           file://0002-Add-subpackage-ptest-which-runs-all-unit-test-cases-.patch \
           file://0001-linux-user-remove-host-stime-syscall.patch \
           "

SRCREV = "521a0dcf59f1ca11e7d9e2f4e1ef3d2dfaebc0e4"

S = "${WORKDIR}/git"

python() {
    d.appendVar('PROVIDES', ' ' + d.getVar('BPN').replace('-qoriq', ''))
    pkgs = d.getVar('PACKAGES').split()
    for p in pkgs:
        if '-qoriq' in p:
            d.appendVar('RPROVIDES_' + p, ' ' + p.replace('-qoriq', ''))
            d.appendVar('RCONFLICTS_' + p, ' ' + p.replace('-qoriq', ''))
            d.appendVar('RREPLACES_' + p, ' ' + p.replace('-qoriq', ''))
}

RDEPENDS_${PN}_class-target += "bash"

EXTRA_OECONF_append_class-target = " --target-list=${@get_qemu_target_list(d)}"
EXTRA_OECONF_append_class-target_mipsarcho32 = "${@bb.utils.contains('BBEXTENDCURR', 'multilib', ' --disable-capstone', '', d)}"
EXTRA_OECONF_append_class-nativesdk = " --target-list=${@get_qemu_target_list(d)}"

do_install_append_class-nativesdk() {
     ${@bb.utils.contains('PACKAGECONFIG', 'gtk+', 'make_qemu_wrapper', '', d)}
}

PACKAGECONFIG ??= " \
    fdt sdl kvm aio libusb \
    ${@bb.utils.filter('DISTRO_FEATURES', 'alsa xen', d)} \
"
PACKAGECONFIG_class-nativesdk ??= "fdt sdl kvm"

PACKAGECONFIG[xkbcommon] = ",,"
PACKAGECONFIG[libudev] = ",,"

DISABLE_STATIC = ""

