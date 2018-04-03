require secure-obj.inc

LIC_FILES_CHKSUM = "file://../README;md5=82b72e88f23cded9dd23f0fb1790b8d2"

DEPENDS += "virtual/kernel"

inherit module 

S = "${WORKDIR}/git/securekeydev"

EXTRA_OEMAKE += 'KERNEL_SRC="${STAGING_KERNEL_DIR}"'
