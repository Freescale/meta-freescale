# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright (C) 2017-2020 NXP
# Copyright (C) 2018 O.S. Systems Software LTDA.
SUMMARY = "Freescale i.MX firmware"
DESCRIPTION = "Freescale i.MX firmware such as for the VPU"

require firmware-imx-${PV}.inc

PE = "1"

inherit allarch

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/imx

    # SDMA Firmware section
    install -d ${D}${nonarch_base_libdir}/firmware/imx/sdma
    install -m 0644 ${S}/firmware/sdma/* ${D}${nonarch_base_libdir}/firmware/imx/sdma
    rm -f ${D}${nonarch_base_libdir}/firmware/imx/sdma/sdma-imx6q.bin
    rm -f ${D}${nonarch_base_libdir}/firmware/imx/sdma/sdma-imx7d.bin

    # EASRC Firmware section
    install -d ${D}${nonarch_base_libdir}/firmware/imx/easrc
    install -m 0644 ${S}/firmware/easrc/* ${D}${nonarch_base_libdir}/firmware/imx/easrc/

    # XCVR Firmware section
    install -d ${D}${nonarch_base_libdir}/firmware/imx/xcvr
    install -m 0644 ${S}/firmware/xcvr/* ${D}${nonarch_base_libdir}/firmware/imx/xcvr/

    # XUVI Firmware section
    install -d ${D}${nonarch_base_libdir}/firmware/imx/xuvi
    install -m 0644 ${S}/firmware/xuvi/* ${D}${nonarch_base_libdir}/firmware/imx/xuvi/

    # EPDC Firmware section
    # NOTE:
    # epdc_ED060XH2C1.fw file has .nonrestricted suffix in the source archive, hence it should
    # be installed with a different name
    install -d ${D}${nonarch_base_libdir}/firmware/imx/epdc
    install -m 0644 ${S}/firmware/epdc/*.fw ${D}${nonarch_base_libdir}/firmware/imx/epdc/
    install -m 0644 ${S}/firmware/epdc/epdc_ED060XH2C1.fw.nonrestricted ${D}${nonarch_base_libdir}/firmware/imx/epdc/epdc_ED060XH2C1.fw

    # HDMI Firmware section
    # NOTE:
    # Only install pre-defined list of firmware files, since the source archive contains
    # also HDMI binary files for imx8m derivatives, which are taken care of by another recipe
    install -d ${D}${nonarch_base_libdir}/firmware/imx/hdmi
    install -m 0644 ${S}/firmware/hdmi/cadence/hdmitxfw.bin ${D}${nonarch_base_libdir}/firmware/imx/hdmi
    install -m 0644 ${S}/firmware/hdmi/cadence/hdmirxfw.bin ${D}${nonarch_base_libdir}/firmware/imx/hdmi
    install -m 0644 ${S}/firmware/hdmi/cadence/dpfw.bin ${D}${nonarch_base_libdir}/firmware/imx/hdmi

    # VPU Firmware section
    # NOTE:
    # Do the same thing as above for HDMI - only install a pre-defined list of firmware files,
    # as some of other files are provided by packages from other recipes.
    install -d ${D}${nonarch_base_libdir}/firmware/vpu
    install -m 0644 ${S}/firmware/vpu/vpu_fw_imx*.bin ${D}${nonarch_base_libdir}/firmware/vpu
}

#
# This prepend is here to produce separate packages containing firmware,
# which could be included separately based on the machine definition.
#
# It operates similar to the FILES mechanism by travesing through
# ${D} + folder supplied as a first parameter, matches the regexp supplied
# as second one, and for every file match - it creates a separate package,
# which contains only files that matches the pattern.
#
python populate_packages:prepend() {
    easrcdir = bb.data.expand('${nonarch_base_libdir}/firmware/imx/easrc', d)
    do_split_packages(d, easrcdir, '^easrc-([^_]*).*\.bin',
                      output_pattern='firmware-imx-easrc-%s',
                      description='Freescale IMX EASRC Firmware [%s]',
                      extra_depends='',
                      prepend=True)

    vpudir = bb.data.expand('${nonarch_base_libdir}/firmware/vpu', d)
    do_split_packages(d, vpudir, '^vpu_fw_([^_]*).*\.bin',
                      output_pattern='firmware-imx-vpu-%s',
                      description='Freescale IMX VPU Firmware [%s]',
                      extra_depends='',
                      prepend=True)

    sdmadir = bb.data.expand('${nonarch_base_libdir}/firmware/imx/sdma', d)
    do_split_packages(d, sdmadir, '^sdma-([^-]*).*\.bin',
                      output_pattern='firmware-imx-sdma-%s',
                      description='Freescale IMX SDMA Firmware [%s]',
                      extra_depends='',
                      prepend=True)

    xcvrdir = bb.data.expand('${nonarch_base_libdir}/firmware/imx/xcvr', d)
    do_split_packages(d, xcvrdir, '^xcvr-([^_]*).*\.bin',
                      output_pattern='firmware-imx-xcvr-%s',
                      description='Freescale IMX XCVR Firmware [%s]',
                      extra_depends='',
                      prepend=True)

    xuvidir = bb.data.expand('${nonarch_base_libdir}/firmware/imx/xuvi', d)
    do_split_packages(d, xuvidir, '^vpu_fw_([^_]*).*\.bin',
                      output_pattern='firmware-imx-xuvi-%s',
                      description='Freescale IMX XUVI Firmware [%s]',
                      extra_depends='',
                      prepend=True)
}

# Declare a contract that we would provide packages produced by prepend above
PACKAGES_DYNAMIC = "${PN}-vpu-* ${PN}-sdma-* ${PN}-easrc-* ${PN}-xcvr-* ${PN}-xuvi-*"

#
# Deal with the rest of Firmware packages here
#
# Provide EPDC and HDMI Firmware in common packages as they tend to be special in
# terms of the content.
#
# NOTE: PACKAGES are defined explicitly here in order to remove the auto-generated
# complimentary packages (-dev and -dbg).
# This is done in order to be able to keep the main package empty and fail when
# somebody tries to install it in the image.
# If -dev package is present in that setup, and dev-pkgs is enabled in
# IMAGE_FEATURES - this leads to a failure during do_rootfs() while all -dev
# packages would be installed, and -dev package would fail because the main one
# is empty.
# Therefore, we opt-out from producing -dev package here, since also for firmware
# files it makes no sense.
PACKAGES = "${PN} ${PN}-epdc ${PN}-hdmi"

FILES:${PN}-epdc = "${nonarch_base_libdir}/firmware/imx/epdc/"
FILES:${PN}-hdmi = "${nonarch_base_libdir}/firmware/imx/hdmi/"

COMPATIBLE_MACHINE = "(imx|use-mainline-bsp)"
