SUMMARY = "lio-utils"
DESCRIPTION = "a simple low-level configuration tool set for the Target+iSCSI (LIO)"
HOMEPAGE = "http://linux-iscsi.org/index.php/Lio-utils"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=c3ea231a32635cbb5debedf3e88aa3df"

SRC_URI = "git://risingtidesystems.com/lio-utils.git;protocal=git"
SRCREV = "lio-4.0"
S = "${WORKDIR}/git"

inherit distutils

EXTRA_OEMAKE += "-C ${S}/tools PREFIX=${prefix} DESTDIR=${D}"

# pass LDFLAGS to linker to avoid missing GNU_HASH QA errors
TARGET_CC_ARCH += "${LDFLAGS}"
do_compile() {
    cd ${S}/tcm-py
    distutils_do_compile

    cd ${S}/lio-py
    distutils_do_compile

    if test -d ${S}/tools; then
        oe_runmake
    fi
}

do_install() {
    cd ${S}/tcm-py
    distutils_do_install
        
    cd ${S}/lio-py
    distutils_do_install
    
    SITE_PACKAGES=${D}/${PYTHON_SITEPACKAGES_DIR}
    [ ! -d ${D}/${sbindir} ] && install -d ${D}/${sbindir}
    for var in tcm_node tcm_dump tcm_loop tcm_fabric lio_dump lio_node; do
        chmod a+x ${SITE_PACKAGES}/${var}.py
        [ ! -f ${D}/${sbindir}/${var} ] && ln -s ${PYTHON_SITEPACKAGES_DIR}/${var}.py ${D}/${sbindir}/${var}
    done

    if test -d ${S}/tools; then
        oe_runmake install
    fi
}

FILES_${PN} += "${sbindir}/*"
