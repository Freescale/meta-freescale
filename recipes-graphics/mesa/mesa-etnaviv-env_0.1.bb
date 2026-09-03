SUMMARY = "Mesa environment variables for etnaviv on xserver"
DESCRIPTION = "Environment variable configuration to enable the etnaviv Mesa driver on the X server."
HOMEPAGE = "https://github.com/Freescale/meta-freescale/"
SECTION = "graphics"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://mesa-etnaviv.sh;beginline=1;endline=1;md5=b2dccaa94b3629a08bfb4f983cad6f89"

SRC_URI = "\
    file://mesa-etnaviv.conf \
    file://mesa-etnaviv.sh \
"

S = "${UNPACKDIR}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install:use-mainline-bsp() {
    # MESA global envirronment variables

    # systemd
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -D -m 644 ${UNPACKDIR}/mesa-etnaviv.conf \
            ${D}${sysconfdir}/systemd/system.conf.d/mesa-etnaviv.conf
    fi

    # sysvinit
    if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}; then
        install -D -m 644 ${UNPACKDIR}/mesa-etnaviv.sh \
            ${D}${sysconfdir}/profile.d/mesa-etnaviv.sh
    fi
}

ALLOW_EMPTY:${PN} = "1"
