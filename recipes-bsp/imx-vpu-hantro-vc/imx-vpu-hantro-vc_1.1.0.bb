# Copyright (C) 2019-2020 NXP

DESCRIPTION = "i.MX VC8000E Encoder library"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=228c72f2a91452b8a03c4cab30f30ef9"

inherit fsl-eula-unpack

SRC_URI = "${FSL_MIRROR}/${BP}.bin;fsl-eula=true"

S = "${WORKDIR}/${BPN}-${PV}"

SRC_URI[md5sum] = "5c254523ae4c44491ea838e84e9aee49"
SRC_URI[sha256sum] = "c5dd1fbf8c9776d7a2844a225e0aa2e489aa24eaab8f55cb48a6e3184def235d"

COMPATIBLE_MACHINE = "(imx8mpevk)"
