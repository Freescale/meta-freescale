SUMMARY = "OPTEE Client"
HOMEPAGE = "https://github.com/qoriq-open-source/optee_client"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=69663ab153298557a59c67a60a743e5b"

inherit pythonnative systemd

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/optee_client;nobranch=1 \
           file://0001-GCC-8-format-truncation-error.patch \
           file://0001-flags-CFLAGS-add-Wno-cpp.patch \
"
S = "${WORKDIR}/git"

SRCREV = "ab3c79ccd3ea9323e236d30037977c0a19944dbd"

EXTRA_OEMAKE = "ARCH=arm64"

do_install() {
    oe_runmake install

    install -D -p -m0755 ${S}/out/export/bin/tee-supplicant ${D}${bindir}/tee-supplicant

    install -D -p -m0644 ${S}/out/export/lib/libteec.so.1.0 ${D}${libdir}/libteec.so.1.0
    ln -sf libteec.so.1.0 ${D}${libdir}/libteec.so
    ln -sf libteec.so.1.0 ${D}${libdir}/libteec.so.1

    cp -a ${S}/out/export/include ${D}/usr/
}

COMPATIBLE_MACHINE = "(qoriq)"
