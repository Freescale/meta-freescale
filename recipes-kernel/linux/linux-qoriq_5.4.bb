LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/linux;nobranch=1 \
    file://0001-Makfefile-linux-5.4-add-warning-cflags-on-LSDK-20.04.patch \
"
SRCREV = "f8118585ee3c7025265b28985fdfe0af96a84466"

require recipes-kernel/linux/linux-qoriq.inc
