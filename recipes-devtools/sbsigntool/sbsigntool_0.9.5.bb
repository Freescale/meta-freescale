SUMMARY = "Utilities for signing UEFI binaries for use with secure boot"

LICENSE = "GPL-3.0-or-later"
LIC_FILES_CHKSUM = "\
    file://LICENSE.GPLv3;md5=9eef91148a9b14ec7f9df333daebc746 \
    file://COPYING;md5=a7710ac18adec371b84a9594ed04fd20 \
"

DEPENDS = "binutils openssl gnu-efi util-linux-libuuid"

SRC_URI = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/jejb/sbsigntools.git;protocol=https;name=sbsigntools;branch=master \
    git://github.com/rustyrussell/ccan.git;protocol=https;destsuffix=${S}/lib/ccan.git;name=ccan;branch=master \
    file://0001-configure-Dont-t-check-for-gnu-efi.patch \
    file://0002-docs-Don-t-build-man-pages.patch \
    file://0003-sbsign-add-x-option-to-avoid-overwrite-existing-sign.patch \
    file://0004-src-Makefile.am-Add-read_write_all.c-to-common_SOURC.patch \
    file://0005-fileio.c-initialize-local-variables-before-use-in-fu.patch \
    file://0006-Makefile.am-do-not-use-Werror.patch \
"

SRCREV_sbsigntools  ?= "9cfca9fe7aa7a8e29b92fe33ce8433e212c9a8ba"
SRCREV_ccan         ?= "b1f28e17227f2320d07fe052a8a48942fe17caa5"
SRCREV_FORMAT       =  "sbsigntools_ccan"


COMPATIBLE_HOST = "(x86_64.*|i.86.*|aarch64.*|arm.*|riscv64.*)-linux"
COMPATIBLE_HOST:armv4 = 'null'

inherit autotools pkgconfig

do_configure:prepend() {
    if [ ! -e ${S}/lib/ccan ]; then
        CC="${BUILD_CC}" CFLAGS="${BUILD_CFLAGS}" LDFLAGS="${BUILD_LDFLAGS}" \
            ${S}/lib/ccan.git/tools/create-ccan-tree \
            --build-type=automake ${S}/lib/ccan \
            talloc read_write_all build_assert array_size endian
    fi

    # These are not in git but required as configure.ac uses gnu strictness
    touch ${S}/AUTHORS ${S}/ChangeLog
}

BBCLASSEXTEND = "native nativesdk"
