SUMMARY = "Python library and tools for doing stuff with EFL files."
DESCRIPTION = "Minimal but very flexible implementation of the expect pattern"
SECTION = "devel/python"
HOMEPAGE = " git://github.com/eliben/pyelftools"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5ce2a2b07fca326bc7c146d10105ccfc"

inherit pypi python3native

SRC_URI[sha256sum] = "89c6da6f56280c37a5ff33468591ba9a124e17d71fe42de971818cbff46c1b24"

BBCLASSEXTEND = "native nativesdk"
