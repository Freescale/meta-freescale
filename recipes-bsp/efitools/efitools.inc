SUMMARY = "Tools to support reading and manipulating the UEFI signature database"
DESCRIPTION = "\
From the EFI Tools package in the Linux user-space, it's now possible \
to read and manipulate the UEFI signatures database via the new \
efi-readvar and efi-updatevar commands. Aside from needing efitools \
1.4, the EFIVARFS file-system is also needed, which was only introduced \
in the Linux 3.8 kernel. \
"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=e28f66b16cb46be47b20a4cdfe6e99a1"

SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/jejb/efitools.git;branch=master \
           file://0001-Fix-for-the-cross-compilation.patch \
           file://0002-Make.rules-define-_GNU_SOURCE-for-a-declaration-of-f.patch \
           file://0006-Makefile-remove-help2man-dependency.patch \
           file://0015-fix-segfault-for-efitools-commands.patch \
           file://0016-Make.rules-Pass-CFLAGS-to-Makefile.patch \
           file://0001-add-binary-only-make-rules.patch \
           "

SRCREV = "392836a46ce3c92b55dc88a1aebbcfdfc5dcddce"

DEPENDS = "openssl-native sbsigntool-native"

PARALLEL_MAKE = ""

COMPATIBLE_HOST = '(i.86|x86_64|aarch64).*-linux'

EXTRA_OEMAKE = "\
    OPENSSL='${STAGING_BINDIR_NATIVE}/openssl' \
    SBSIGN='${STAGING_BINDIR_NATIVE}/sbsign' \
    NM='${NM}' AR='${AR}' \
    OPENSSL_LIB='${STAGING_LIBDIR_NATIVE}' \
    EXTRA_LDFLAGS='${LDFLAGS}' \
    CFLAGS='${CFLAGS}' \
    OBJCOPY='${OBJCOPY}' \
"
EXTRA_OEMAKE:append:x86 = " ARCH=ia32"
EXTRA_OEMAKE:append:x86-64 = " ARCH=x86_64"
EXTRA_OEMAKE:append:aarch64 = " ARCH=aarch64"

EFI_BOOT_PATH = "/boot/efi/EFI/BOOT"

do_install() {
    oe_runmake install DESTDIR='${D}${base_prefix}'
}
