DESCRIPTION = "CST Tool"
SECTION = "cst"
LICENSE = "BSD"

# TODO: fix license - this file is not a license
LIC_FILES_CHKSUM = "file://RELEASENOTES;beginline=8;endline=43;md5=5a7b22a2c96b5f94e0498c5f413aa8d3"

DEPENDS += "openssl"

SRC_URI = "git://git.freescale.com/ppc/sdk/cst.git;nobranch=1"
SRCREV = "55223ed64404ee57c55416017a8d65b28314282e"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'OPENSSL_LIB_PATH=${STAGING_LIBDIR} OPENSSL_INC_PATH=${STAGING_INCDIR} CC="${CC}" LD="${CC}" LDFLAGS="${LDFLAGS}"'

do_install () {
	install -d ${D}/${bindir}/cst
	install -m 755 ${S}/gen_keys ${D}/${bindir}/cst/
	install -m 755 ${S}/gen_otpmk ${D}/${bindir}/cst/
	install -m 755 ${S}/uni_cfsign ${D}/${bindir}/cst/
	install -m 755 ${S}/uni_sign ${D}/${bindir}/cst/
	cp -rf ${S}/input_files ${D}/${bindir}/cst
}

BBCLASSEXTEND = "native nativesdk"
PARALLEL_MAKE = ""

FILES_${PN}-dbg += "${bindir}/cst/.debug"
