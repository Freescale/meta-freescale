SUMMARY = "OPTEE Client"
HOMEPAGE = "https://github.com/OP-TEE/optee_client"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=69663ab153298557a59c67a60a743e5b"

inherit python3native systemd

SRC_URI = "git://github.com/OP-TEE/optee_client;nobranch=1"
SRCREV = "be4fa2e36f717f03ca46e574aa66f697a897d090"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "ARCH=arm64"

do_install() {
    oe_runmake install

    install -D -p -m0755 ${S}/out/export/usr/sbin/tee-supplicant ${D}${bindir}/tee-supplicant
    install -D -p -m0755 ${S}/out/export/usr/lib/libteec.so.1.0.0 ${D}${libdir}/libteec.so.1.0.0
    ln -sf libteec.so.1.0.0 ${D}${libdir}/libteec.so.1.0
    ln -sf libteec.so.1.0.0 ${D}${libdir}/libteec.so.1
    ln -sf libteec.so.1 ${D}${libdir}/libteec.so

    cp -a ${S}/out/export/usr/include ${D}/usr/
}

COMPATIBLE_MACHINE = "(qoriq-arm64)"
