SUMMARY = "GPU libraries"
DESCRIPTION = "GPU libraries for Linux"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=6c12031a11b81db21cdfe0be88cac4b3"

SRC_URI = "http://www.nxp.com/lgfiles/sdk/lsdk1909/gpulib-lsdk1909.bin;fsl-eula=true"
SRC_URI[md5sum] = "a68ba3b91a12dd7da0d573891e20db78"
SRC_URI[sha256sum] = "0fcbf785dfe7402bc03a808e6ced754daeddd387391ebf6e15131187ff6e7bc1"

S = "${WORKDIR}/gpulib-lsdk1909"

inherit fsl-eula-unpack

do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}/opt ${D}${libdir} ${D}/usr/include
    cd  ls1028a/linux
    cp -a gpu-demos/opt/viv_samples/* ${D}/opt
    cp -a gpu-core/usr/include/* ${D}/usr/include 
    cp -a gpu-core/usr/lib/* ${D}/usr/lib
}

PACKAGES = "${PN}-dbg ${PN}"
FILES_${PN} = "${libdir}/* /opt/* /usr/include/*"
INSANE_SKIP_${PN} += "already-stripped file-rdeps dev-so host-user-contaminated"
COMPATIBLE_MACHINE = "(qoriq)"
