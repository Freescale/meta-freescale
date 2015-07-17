DESCRIPTION = "skmm host driver offload data to PCIe EP and push the data en-decrypted back to application"
SECTION = "c293-skmm-host"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://Makefile;endline=7;md5=edffaac1da9e809ade0d2fcfcc18d8df"

inherit  module qoriq_build_64bit_kernel

SRC_URI = "git://git.freescale.com/ppc/sdk/skmm-host.git;nobranch=1"
SRCREV = "a655c571a92f4a5d6ced09869c2f17609ee47361"

S = "${WORKDIR}/git"

EXTRA_OEMAKE='KERNEL_DIR="${STAGING_KERNEL_DIR}" PREFIX="${D}"'

FILES_${PN} += "/etc/skmm/ /usr/bin/"
