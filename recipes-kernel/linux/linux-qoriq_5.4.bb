LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

LINUX_VERSION = "5.4.3"

SRC_URI = "git://github.com/nxp-qoriq/linux;protocol=https;nobranch=1 \
    file://0001-Makfefile-linux-5.4-add-warning-cflags-on-LSDK-20.04.patch \
    file://0001-perf-Make-perf-able-to-build-with-latest-libbfd.patch \
    file://0001-perf-tests-bp_account-Make-global-variable-static.patch \
    file://0001-perf-cs-etm-Move-definition-of-traceid_list-global-v.patch \
    file://0001-perf-bench-Share-some-global-variables-to-fix-build-.patch \
    file://0001-libtraceevent-Fix-build-with-binutils-2.35.patch \
"
SRCREV = "134788b16485dd9fa81988681d2365ee38633fa2"

require recipes-kernel/linux/linux-qoriq.inc
