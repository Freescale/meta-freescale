HOMEPAGE = "http://cryptodev-linux.org/"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

python() {
    pkgs = d.getVar('PACKAGES', True).split()
    for p in pkgs:
        if 'cryptodev-qoriq' in p:
            d.appendVar("RPROVIDES_%s" % p, p.replace('cryptodev-qoriq', 'cryptodev'))
            d.appendVar("RCONFLICTS_%s" % p, p.replace('cryptodev-qoriq', 'cryptodev'))
            d.appendVar("RREPLACES_%s" % p, p.replace('cryptodev-qoriq', 'cryptodev'))
}

FILESEXTRAPATHS_prepend := "${THISDIR}/yocto_patches:"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/cryptodev-linux;nobranch=1"
SRCREV = "f365c69d7852d6579952825c9f90a27129f92d22"

# NOTE: remove this patch and all traces of DISTRO_FEATURE c29x_pkc
# if pkc-host does not need customized cryptodev patches anymore
#SRC_URI_append = "${@bb.utils.contains('DISTRO_FEATURES', 'c29x_pkc', ' file://0001-don-t-advertise-RSA-keygen.patch', '', d)}"

S = "${WORKDIR}/git"

CLEANBROKEN = "1"
