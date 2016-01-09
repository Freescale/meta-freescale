DESCRIPTION = "skmm host driver offload data to PCIe EP and push the data en-decrypted back to application"
SECTION = "c293-skmm-host"
LICENSE = "BSD & GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=99803d8e9a595c0bdb45ca710f353813"

inherit  module qoriq_build_64bit_kernel

SRC_URI = "git://git.freescale.com/ppc/sdk/skmm-host.git;nobranch=1"
SRCREV = "a655c571a92f4a5d6ced09869c2f17609ee47361"

S = "${WORKDIR}/git"

EXTRA_OEMAKE='KERNEL_DIR="${STAGING_KERNEL_DIR}" PREFIX="${D}"'

FILES_${PN} += "/etc/skmm/ /usr/bin/"

COMPATIBLE_MACHINE = "(p4080ds|t4240qds)"
