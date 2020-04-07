LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/linux;nobranch=1 \
    file://0001-Makfefile-add-cflags.patch \
"
SRCREV = "c5f3ab9ac163f5c86a5c1ed163afaf24f2eea669"

require recipes-kernel/linux/linux-qoriq.inc
