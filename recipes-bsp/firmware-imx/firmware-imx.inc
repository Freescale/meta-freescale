SUMMARY = "Freescale IMX firmware"
DESCRIPTION = "Freescale IMX firmware such as for the VPU"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=3880bb9c943b135a30fad5e8aabd3ee9"

PE = "1"

SRC_URI = "${FSL_MIRROR}/firmware-imx-${PV}.bin;fsl-eula=true"

inherit fsl-eula-unpack

do_install() {
    install -d ${D}/lib/firmware/imx
    cp -rfv firmware/* ${D}/lib/firmware/

    mv ${D}/lib/firmware/epdc/ ${D}/lib/firmware/imx/epdc/
    mv ${D}/lib/firmware/imx/epdc/epdc_ED060XH2C1.fw.nonrestricted ${D}/lib/firmware/imx/epdc/epdc_ED060XH2C1.fw

    find ${D}/lib/firmware -type f -exec chmod 644 '{}' ';'
    find ${D}/lib/firmware -type f -exec chown root:root '{}' ';'

    # Remove files not going to be installed
    find ${D}/lib/firmware/ -name '*.mk' -exec rm '{}' ';'
}

python populate_packages_prepend() {
    vpudir = bb.data.expand('/lib/firmware/vpu', d)
    do_split_packages(d, vpudir, '^vpu_fw_([^_]*).*\.bin',
                      output_pattern='firmware-imx-vpu-%s',
                      description='Freescale IMX Firmware %s',
                      extra_depends='',
                      prepend=True)

    sdmadir = bb.data.expand('/lib/firmware/sdma', d)
    do_split_packages(d, sdmadir, '^sdma-([^-]*).*\.bin',
                      output_pattern='firmware-imx-sdma-%s',
                      description='Freescale IMX Firmware %s',
                      extra_depends='',
                      prepend=True)
}

ALLOW_EMPTY_${PN} = "1"

PACKAGES_DYNAMIC = "${PN}-vpu-* ${PN}-sdma-*"

PACKAGES =+ "${PN}-epdc"

FILES_${PN}-epdc = "/lib/firmware/imx/epdc/"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
