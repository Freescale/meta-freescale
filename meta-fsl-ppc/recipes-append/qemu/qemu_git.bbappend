SRC_URI = "git://git.am.freescale.net/gitolite/sdk/qemu.git;protocol=git"
SRCREV = "${AUTOREV}"

QEMU_TARGETS ?= "ppc"

PR .= "+${DISTRO}.0"
