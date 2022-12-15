LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

LINUX_VERSION = "5.10.52"

SRC_URI = "git://github.com/nxp-qoriq/linux;protocol=https;nobranch=1"
SRCREV = "a11753a89ec610768301d4070e10b8bd60fde8cd"

require recipes-kernel/linux/linux-qoriq.inc
