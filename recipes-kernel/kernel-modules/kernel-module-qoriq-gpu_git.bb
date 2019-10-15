SECTION = "devel"
SUMMARY = "GPU KERNEL MODULE"
DESCRIPTION = "The gpu-modules package contains the gpu kernel modules"
LICENSE = "GPLv2 & MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5ab1a30d0cd181e3408077727ea5a2db \
                    "
inherit module fsl-eula-unpack

SRC_URI = "http://www.nxp.com/lgfiles/sdk/lsdk1909/gpu-module-lsdk1909.bin;;fsl-eula=true \
           file://0001-Makfile-add-modules_install.patch \
           "
SRC_URI[md5sum] = "9f9591530fe7b3f46361bb932901c509"
SRC_URI[sha256sum] = "8be4ab0d817e66ef685a6a8f3d95ec1e70e9eae73b4f5a836b4b60ebcf0d588d"

S = "${WORKDIR}/gpu-module-lsdk1909"

export INSTALL_MOD_DIR="kernel/gpu-modules"

EXTRA_OEMAKE += "KERNEL_DIR='${STAGING_KERNEL_DIR}'"

COMPATIBLE_MACHINE = "(qoriq)"
