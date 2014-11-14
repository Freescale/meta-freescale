require recipes-kernel/linux/linux-qoriq.inc

SRC_URI = "git://git.freescale.com/ppc/sdk/linux.git;nobranch=1 \
    file://powerpc-Fix-64-bit-builds-with-binutils-2.24.patch \
    file://Fix-for-CVE-2014-5045-fs-umount-on-symlink-leak.patch \
    file://Fix-CVE-2014-5077-sctp-inherit-auth-capable-on-INIT-collisions.patch \
    file://Fix-CVE-2014-5471_CVE-2014-5472.patch \
    file://remove-Altivec-from-T1040-64-bit-defconfig.patch \
    file://configure-T1040-for-FMAN-V3L.patch \
    file://modify-defconfig-t1040-nr-cpus.patch \
"
SRCREV = "c29fe1a733308cbe592b3af054a97be1b91cf2dd"

