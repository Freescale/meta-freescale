require recipes-kernel/linux/linux-qoriq.inc

SRC_URI = "git://git.freescale.com/ppc/sdk/linux.git;nobranch=1 \
    file://powerpc-Fix-64-bit-builds-with-binutils-2.24.patch \
    file://Fix-for-CVE-2014-5045-fs-umount-on-symlink-leak.patch \
    file://Fix-CVE-2014-5077-sctp-inherit-auth-capable-on-INIT-collisions.patch \
    file://Fix-CVE-2014-5471_CVE-2014-5472.patch \
    file://modify-defconfig-t1040-nr-cpus.patch \
    file://0001-mnt-CVE-2014-5206_CVE-2014-5207.patch \
    file://0002-mnt-CVE-2014-5206_CVE-2014-5207.patch \
    file://0003-mnt-CVE-2014-5206_CVE-2014-5207.patch \
    file://0004-mnt-CVE-2014-5206_CVE-2014-5207.patch \
    file://0005-mnt-CVE-2014-5206_CVE-2014-5207.patch \
    file://udf-CVE-2014-6410.patch \
"
SRCREV = "6619b8b55796cdf0cec04b66a71288edd3057229"

