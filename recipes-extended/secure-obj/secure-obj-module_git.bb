require secure-obj.inc

LIC_FILES_CHKSUM = "file://../LICENSE;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS += "virtual/kernel"

inherit module 

S = "${WORKDIR}/git/securekeydev"

EXTRA_OEMAKE += 'KERNEL_SRC="${STAGING_KERNEL_DIR}"'
