LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

LINUX_VERSION = "5.4.47"

SRC_URI = "git://github.com/nxp-qoriq/qoriq-components/linux;nobranch=1 \
    file://0001-Makfefile-linux-5.4-add-warning-cflags-on-LSDK-20.04.patch \
    file://0001-perf-tests-bp_account-Make-global-variable-static.patch \
    file://0001-perf-cs-etm-Move-definition-of-traceid_list-global-v.patch \
    file://0001-perf-bench-Share-some-global-variables-to-fix-build-.patch \
    file://0001-libtraceevent-Fix-build-with-binutils-2.35.patch \
"
SRCREV = "6bff40d413b394c2d742e7a42089bfc62aef0a9b"

require recipes-kernel/linux/linux-qoriq.inc
