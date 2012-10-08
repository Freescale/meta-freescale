require firmware-imx.inc

PR = "${INC_PR}.2"

SRC_URI += "file://vpu_fw_imx51.bin \
            file://vpu_fw_imx53.bin"

SRC_URI[md5sum] = "64c77a0f061fe7e628d26fda3c5c7f5a"
SRC_URI[sha256sum] = "072e9b712db691a799182d29473d5b7ed9dc9d095a6d0053ef42a84db7709a42"

do_patch() {
	cp ${WORKDIR}/vpu_fw_imx51.bin ${S}/firmware/vpu/
	cp ${WORKDIR}/vpu_fw_imx53.bin ${S}/firmware/vpu/
}

COMPATIBLE_MACHINE = "(mx5)"
