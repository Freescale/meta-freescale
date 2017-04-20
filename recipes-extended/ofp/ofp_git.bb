SUMMARY = "An open source user space fast path TCP/IP stack" 
DESCRIPTION = "openfastpath is used to enable accelerated routing/forwarding for IPv4 and IPv6, \
               tunneling and termination for a variety of protocols."
HOMEPAGE = "http://www.openfastpath.org"
SECTION = "console/network"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fbe4957c430eed6cc20521d4eb429fae"

SRC_URI = "git://github.com/OpenFastPath/ofp.git;protocol=https \
           file://0001-Patch-to-install-ofp-example-applications.patch \
           file://Fix-compilation-issue-with-redefinition-of-a-struct.patch \
"
SRCREV = "f7ea51b32450ba45d87aff79504e39fad801e7a7"

S = "${WORKDIR}/git"

inherit autotools-brokensep pkgconfig

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "odp"

EXTRA_OECONF = " \
    --prefix=/usr \
    --libdir=${libdir} \
    --host=${SIMPLE_TARGET_SYS} \
    --with-odp=${STAGING_DIR_TARGET} \
"

do_configure () {
    export SIMPLE_TARGET_SYS="$(echo ${TARGET_SYS} | sed s:${TARGET_VENDOR}::g)"

    ${S}/bootstrap
    ${S}/configure ${EXTRA_OECONF}
}

FILES_${PN} += "/usr/ofp/bin"
FILES_${PN}-dbg += "/usr/ofp/bin/.debug"
COMPATIBLE_MACHINE = "(ls2088a)"
