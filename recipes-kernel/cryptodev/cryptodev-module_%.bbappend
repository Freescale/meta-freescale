FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# Fix build for Linux 6.7-rc1: https://github.com/cryptodev-linux/cryptodev-linux/commit/5e7121e45ff283d30097da381fd7e97c4bb61364
# Since Linux 6.7-rc1, no ahash algorithms set a nonzero alignmask,
# and therefore `crypto_ahash_alignmask` has been removed.
#
# See also: https://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git/commit/?id=0f8660c82b79af595b056f6b9f4f227edeb88574
#           https://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git/commit/?id=c626910f3f1bbce6ad18bc613d895d2a089ed95e

SRC_URI:append:imx-generic-bsp = " \
    file://fix-build-for-Linux-6.7-rc1.patch \
"
