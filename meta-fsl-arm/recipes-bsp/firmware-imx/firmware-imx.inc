SUMMARY = "Freescale IMX firmware"
DESCRIPTION = "Freescale IMX firmware such as for the VPU"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://licenses/vpu/EULA;md5=6469a8514eed615d6bf8c09f41846d8f"

INC_PR = "r1"

SRC_URI = "file://firmware-imx-${PV}.tar.gz \
           file://vpu_fw_imx51.bin \
           file://vpu_fw_imx53.bin"

COMPATIBLE_MACHINE = "(mx5)"

# todo: make FILES_ machine dependent using override, i.MX53 only now.
FILES_${PN} = "/lib/firmware/vpu/vpu_fw_imx53.bin"
FILES_${PN} += "/lib/firmware/sdma/sdma-imx53-to1.bin"
FILES_${PN} += "/lib/firmware/ath6k/*"
FILES_${PN} += "/lib/firmware/ar3k/*"

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
	cp -rf ${S}/firmware/ath6k ${D}/lib/firmware
	cp -rf ${S}/firmware/ar3k ${D}/lib/firmware
}

# Add an upgrade path
RPROVIDES_${PN} = "imx-firmware"
RREPLACES_${PN} = "imx-firmware"
RCONFLICTS_${PN} = "imx-firmware"
