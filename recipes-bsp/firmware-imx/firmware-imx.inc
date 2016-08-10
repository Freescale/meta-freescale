SUMMARY = "Freescale IMX firmware"
DESCRIPTION = "Freescale IMX firmware such as for the VPU"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=8cf95184c220e247b9917e7244124c5a"

PE = "1"

SRC_URI = "${FSL_MIRROR}/firmware-imx-${PV}.bin;fsl-eula=true \
           git://git.freescale.com/imx/imx-firmware.git;branch=${SRCBRANCH};destsuffix=${S}/git"

inherit fsl-eula-unpack

do_install() {
    install -d ${D}${base_libdir}/firmware/imx
    install -d ${D}${base_libdir}/firmware/bcm

    cp -rfv firmware/* ${D}${base_libdir}/firmware/
    cp -rfv firmware/* ${D}${base_libdir}/firmware/
    cp -rfv git/BCM4339/TypeZP/BCM4339_wifi/*.bin ${D}${base_libdir}/firmware/bcm
    cp -rfv git/BCM4339/TypeZP/BCM4339_BT/*.hcd ${D}${base_libdir}/firmware/

    mv ${D}${base_libdir}/firmware/epdc/ ${D}${base_libdir}/firmware/imx/epdc/
    mv ${D}${base_libdir}/firmware/imx/epdc/epdc_ED060XH2C1.fw.nonrestricted ${D}${base_libdir}/firmware/imx/epdc/epdc_ED060XH2C1.fw

    find ${D}${base_libdir}/firmware -type f -exec chmod 644 '{}' ';'
    find ${D}${base_libdir}/firmware -type f -exec chown root:root '{}' ';'

    # Remove files not going to be installed
    find ${D}${base_libdir}/firmware/ -name '*.mk' -exec rm '{}' ';'
}

python populate_packages_prepend() {
    vpudir = bb.data.expand('${base_libdir}/firmware/vpu', d)
    do_split_packages(d, vpudir, '^vpu_fw_([^_]*).*\.bin',
                      output_pattern='firmware-imx-vpu-%s',
                      description='Freescale IMX Firmware %s',
                      extra_depends='',
                      prepend=True)

    sdmadir = bb.data.expand('${base_libdir}/firmware/sdma', d)
    do_split_packages(d, sdmadir, '^sdma-([^-]*).*\.bin',
                      output_pattern='firmware-imx-sdma-%s',
                      description='Freescale IMX Firmware %s',
                      extra_depends='',
                      prepend=True)

}

ALLOW_EMPTY_${PN} = "1"

PACKAGES_DYNAMIC = "${PN}-vpu-* ${PN}-sdma-*"

PACKAGES =+ "${PN}-epdc ${PN}-bcm4339"

FILES_${PN}-epdc = "${base_libdir}/firmware/imx/epdc/"
FILES_${PN}-bcm4339 = "${base_libdir}/firmware/*.hcd ${base_libdir}/firmware/bcm/*"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
