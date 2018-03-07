SUMMARY = "OPTEE Client"
HOMEPAGE = "https://github.com/qoriq-open-source/optee_client"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=69663ab153298557a59c67a60a743e5b"

inherit pythonnative systemd

SRC_URI = "git://github.com/qoriq-open-source/optee_client.git;nobranch=1 \
           file://0001-Respect-LDFLAGS-set-from-OE-build.patch \
"
S = "${WORKDIR}/git"

SRCREV = "73b4e490a8ed0b4a7714818e80998b9d8a7da958"

EXTRA_OEMAKE = "ARCH=arm64"

do_install() {
    oe_runmake install

    install -D -p -m0755 ${S}/out/export/bin/tee-supplicant ${D}${bindir}/tee-supplicant

    install -D -p -m0644 ${S}/out/export/lib/libteec.so.1.0 ${D}${libdir}/libteec.so.1.0
    ln -sf libteec.so.1.0 ${D}${libdir}/libteec.so
    ln -sf libteec.so.1.0 ${D}${libdir}/libteec.so.1

    cp -a ${S}/out/export/include ${D}/usr/
}
