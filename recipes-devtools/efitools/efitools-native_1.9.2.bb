SUMMARY = "Tools to support reading and manipulating the UEFI signature database"
DESCRIPTION = "Host tools from efitools to create and sign UEFI signature lists (cert-to-efi-sig-list, sign-efi-sig-list and friends). U-Boot runs cert-to-efi-sig-list to embed the EFI capsule authentication key."
HOMEPAGE = "https://git.kernel.org/pub/scm/linux/kernel/git/jejb/efitools.git"
SECTION = "devel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=e28f66b16cb46be47b20a4cdfe6e99a1"

# The host tools only need OpenSSL and the gnu-efi headers. sbsigntool and the
# File::Slurp Perl module are only used to sign and embed the EFI
# applications, which this recipe does not build.
DEPENDS = "\
    gnu-efi-native \
    openssl-native \
"

SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/jejb/efitools.git;protocol=https;branch=master \
           file://0001-Fix-for-the-cross-compilation.patch \
           file://0002-Make.rules-define-_GNU_SOURCE-for-a-declaration-of-f.patch \
           file://0003-Fix-the-wrong-dependency-for-blacklist.esl.patch \
           file://0004-LockDown-run-system-warm-reset-after-the-key-provisi.patch \
           file://0005-Allow-to-override-tools-for-target-build.patch \
           file://0006-Makefile-remove-help2man-dependency.patch \
           file://0007-Add-static-keyword-for-IsValidVariableHeader.patch \
           file://0008-Dynamically-load-openssl.cnf-for-openssl-1.0.x-1.1.x.patch \
           file://0009-console.c-Fix-compilation-against-latest-usr-include.patch \
           file://0010-LockDown-enable-the-enrollment-for-DBX.patch \
           file://0011-LockDown-show-the-error-message-with-3-sec-timeout.patch \
           file://0012-Makefile-do-not-build-signed-efi-image.patch \
           file://0013-Build-DBX-by-default.patch \
           file://0014-LockDown-disable-the-entrance-into-BIOS-setup-to-re-.patch \
           file://0015-fix-segfault-for-efitools-commands.patch \
           file://0016-Make.rules-Pass-CFLAGS-to-Makefile.patch \
           file://0017-Make.rules-fix-efi-files-for-gnu-efi-3.0.18.patch \
           file://0001-add-binary-only-make-rules.patch \
           "
SRCREV = "392836a46ce3c92b55dc88a1aebbcfdfc5dcddce"

inherit_defer native

PARALLEL_MAKE = ""

# OpenSSL 4.0 removed the ENGINE API. Turn its calls into the stubs OpenSSL
# provides for source compatibility; loading keys through an engine then
# fails at runtime, while file-based keys keep working.
CFLAGS:append = " -DOPENSSL_ENGINE_STUBS"

EXTRA_OEMAKE = "\
    OPENSSL='${STAGING_BINDIR_NATIVE}/openssl' \
    NM='${NM}' AR='${AR}' \
    OPENSSL_LIB='${STAGING_LIBDIR_NATIVE}' \
    EXTRA_LDFLAGS='${LDFLAGS}' \
    CFLAGS='${CFLAGS}' \
    OBJCOPY='${OBJCOPY}' \
    INCDIR_PREFIX='${STAGING_DIR_NATIVE}' \
    CRTPATH_PREFIX='${STAGING_DIR_NATIVE}' \
"

do_compile() {
    # Only the host tools, not the EFI applications.
    oe_runmake binaries
}

do_install() {
    oe_runmake install-binaries DESTDIR='${D}${base_prefix}'
}
