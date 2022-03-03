SUMMARY = "crconf -Linux crypto layer configuraton tool"
SECTION = "base"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://Makefile;beginline=1;endline=5;md5=0f77fc44eb5911007ae4ac9f6736e111"

EXTRA_OEMAKE = "'CC=${CC}' 'HOSTCC=${CC}' SBINDIR='${sbindir}' MANDIR='${mandir}'"

SRC_URI = "git://git.code.sf.net/p/crconf/code;protocol=https;nobranch=1"
SRCREV = "c2b9595d739a9515a86ff3b1980b5cfdfcc42d68"

S = "${WORKDIR}/git"

do_install () {
       oe_runmake install  DESTDIR=${D}
}
