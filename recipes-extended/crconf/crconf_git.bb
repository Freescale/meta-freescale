SUMMARY = "crconf -Linux crypto layer configuraton tool"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://Makefile;beginline=1;endline=5;md5=0f77fc44eb5911007ae4ac9f6736e111"

SRC_URI = "git://github.com/Thermi/crconf.git;protocol=https;nobranch=1"

EXTRA_OEMAKE = "'CC=${CC}' 'HOSTCC=${CC}'"

SRCREV = "7b5819e7638e471d41dd2dca71f012d5a022f014"

S = "${WORKDIR}/git"

do_install () {
       oe_runmake install  DESTDIR=${D}
}

FILES_${PN} += "/share/man/*  ${libdir}/* ${sbindir}/*"
