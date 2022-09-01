# Copyright (C) 2013-2018 O.S. Systems Software LTDA.
# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright (C) 2017-2020 NXP

DESCRIPTION = "Freescale VPU library for Chips&Media VPU"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=228c72f2a91452b8a03c4cab30f30ef9"

PROVIDES = "virtual/imxvpu"
RPROVIDES:${PN} = "virtual/imxvpu"

PE = "1"

SRC_URI = "${FSL_MIRROR}/${BP}.bin;fsl-eula=true"
SRC_URI[md5sum] = "6d6302189a6704874375afe62a65def0"
SRC_URI[sha256sum] = "87cb799a57df654db29403cb74a75ca5185a1517022d3a4a16b8d69056c36127"

inherit fsl-eula-unpack use-imx-headers

PLATFORM = "IMX6Q"

do_compile() {
    INCLUDE_DIR="-I${STAGING_INCDIR_IMX}"
    oe_runmake CROSS_COMPILE="${HOST_PREFIX}" PLATFORM="${PLATFORM}" INCLUDE="${INCLUDE_DIR}" all
}

do_install() {
    oe_runmake PLATFORM="${PLATFORM}" DEST_DIR="${D}" install
}

COMPATIBLE_MACHINE = "(mx6q-nxp-bsp|mx6dl-nxp-bsp)"
