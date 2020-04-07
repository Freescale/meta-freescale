LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/linux;nobranch=1 \
    file://0001-Makfefile-add-cflags.patch \
    file://0001-perf-tools-Add-Python-3-support.patch \
"
SRCREV = "d39cc9ffcbe5638b50f5f45698eb87a6c3a96eb3"

require recipes-kernel/linux/linux-qoriq.inc
