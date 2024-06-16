SUMMARY = "Mesa environment variables for etnaviv on xserver"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "\
    file://mesa-etnaviv.conf \
    file://mesa-etnaviv.sh \
"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install:use-mainline-bsp() {
    # MESA global envirronment variables

    # systemd
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -D -m 644 ${WORKDIR}/mesa-etnaviv.conf \
            ${D}${sysconfdir}/systemd/system.conf.d/mesa-etnaviv.conf
    fi

    # sysvinit
    if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}; then
        install -D -m 644 ${WORKDIR}/mesa-etnaviv.sh \
            ${D}${sysconfdir}/profile.d/mesa-etnaviv.sh
    fi
}

ALLOW_EMPTY:${PN} = "1"
