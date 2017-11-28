SUMMARY = "X.org graphics driver for KMS based systems with pluggable GPU backend"
DESCRIPTION = "The xf86-video-armada module is a 2D graphics driver for the X Window \
System as implemented by X.org, supporting these DRM KMS drivers: \
\
	Freescale i.MX \
	Marvell Armada 510 (Dove) \
\
and GPU drivers: \
	Vivante libGAL (Armada only) \
	Etnaviv (Armada and i.MX) with galcore kernel driver \
	Etnaviv (Armada and i.MX) with etnaviv DRM kernel driver \
"
LICENSE = "BSD"

require recipes-graphics/xorg-driver/xorg-driver-video.inc

DEPENDS += "libdrm-armada"

LIC_FILES_CHKSUM = " \
    file://README;md5=d5271074fb6ad959b7b6cfa68b4adaf0 \
    file://../etna_viv/LICENSE;md5=9d4853905d85f044ed013e75def30a76 \
    "

SRCREV_armada = "04748ff4fb30370086cc97b9487a32951c5600ba"
SRCREV_etna = "f64d77abbb54433bd5de955c20afddc6eb4f4cb1"
SRCREV_FORMAT = "armada_etna"

PV = "0.1+git${SRCPV}"

SRC_URI = " \
    git://git.arm.linux.org.uk/cgit/xf86-video-armada.git;branch=unstable-devel;protocol=http;name=armada \
    git://github.com/laanwj/etna_viv.git;protocol=https;name=etna;destsuffix=etna_viv \
    "

S = "${WORKDIR}/git"

RDEPENDS_${PN} = "xserver-xorg-module-exa \
                  xserver-xorg-extension-dri \
                  xserver-xorg-extension-dri2 \
                  xserver-xorg-extension-glx \
                  "

EXTRA_OECONF = "--disable-etnaviv \
                --disable-vivante \
                --with-etnaviv-source=${WORKDIR}/etna_viv \
                "
