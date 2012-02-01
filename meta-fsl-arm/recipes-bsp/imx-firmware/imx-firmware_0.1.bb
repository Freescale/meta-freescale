SUMMARY = "Freescale IMX firmware"
DESCRIPTION = "Freescale IMX firmware such as for the VPU"
SECTION = "base"
LICENSE = "Freescale"
LIC_FILES_CHKSUM = "file://licenses/vpu/EULA;md5=6469a8514eed615d6bf8c09f41846d8f"
PR = "r3"

SRC_URI = "file://firmware-imx-11.09.01.tar.gz \
           file://vpu_fw_imx51.bin \
           file://vpu_fw_imx53.bin"

COMPATIBLE_MACHINE = "(imx53ard|imx53qsb|imx51evk)"

S = "${WORKDIR}/firmware-imx-11.09.01"

# todo: make FILES_ machine dependent using override, i.MX53 only now.
FILES_${PN} = "/lib/firmware/vpu/vpu_fw_imx53.bin"
FILES_${PN} += "/lib/firmware/sdma/sdma-imx53-to1.bin"

do_patch() {
	cp ${WORKDIR}/vpu_fw_imx51.bin ${S}/firmware/vpu/
	cp ${WORKDIR}/vpu_fw_imx53.bin ${S}/firmware/vpu/
}

do_install() {
	install -d ${D}/lib/firmware/vpu
	install -m 0755 ${S}/firmware/vpu/vpu_fw_imx53.bin ${D}/lib/firmware/vpu
        chmod 644 ${D}/lib/firmware/vpu/vpu_fw_imx53.bin
	install -d ${D}/lib/firmware/sdma
	install -m 0755 ${S}/firmware/sdma/sdma-imx53-to1.bin ${D}/lib/firmware/sdma
        chmod 644 ${D}/lib//firmware/sdma/sdma-imx53-to1.bin
}

