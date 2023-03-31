HOMEPAGE = "http://cryptodev-linux.org/"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

python() {
    d.appendVar('PROVIDES', ' ' + d.getVar('BPN').replace('-qoriq', ''))
    pkgs = d.getVar('PACKAGES').split()
    for p in pkgs:
        if '-qoriq' in p:
            d.appendVar("RPROVIDES_" + p, ' ' + p.replace('-qoriq', ''))
            d.appendVar("RCONFLICTS_" + p, ' ' + p.replace('-qoriq', ''))
            d.appendVar("RREPLACES_" + p, ' ' + p.replace('-qoriq', ''))
}

SRC_URI = "git://github.com/nxp-qoriq/cryptodev-linux;protocol=https;nobranch=1"
SRCREV = "f3007431120a1b4ae983c2b9ea09051311aa5e2f"

S = "${WORKDIR}/git"

CLEANBROKEN = "1"
