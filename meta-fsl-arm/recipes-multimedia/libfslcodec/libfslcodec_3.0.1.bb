# Copyright (C) 2012 Freescale Semicondutors
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Freescale Multimedia codec libs"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://EULA.txt;md5=ea4d5c069d7aef0838a110409ea78a01"

inherit fsl-eula-unpack autotools pkgconfig

SRC_URI[md5sum] = "9e0a765de73b32efa5d276fa90372ce1"
SRC_URI[sha256sum] = "882f223375652b639b7167adca6858dd7c7f1f1cf4d3c6b5b62eec2466bd53be"
SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"

PACKAGES_DYNAMIC = "${PN}-*"

do_install_append() {
	# FIXME: This link points to nowhere
	rm ${D}${libdir}/imx-mm/audio-codec/lib_src_ppp_arm11_elinux.so

	# FIXME: Drop examples
	rm -r ${D}${datadir}/imx-mm
}

python populate_packages_prepend() {
    audiodir = bb.data.expand('${libdir}/imx-mm/audio-codec', d)
    do_split_packages(d, audiodir, '^lib_(.*)_elinux\.so\..*',
                      aux_files_pattern_verbatim='${libdir}/imx-mm/audio-codec/lib_%s_elinux.so.*',
                      output_pattern='libfslcodec-audio-%s',
                      description='Freescale IMX Codec (%s)',
                      extra_depends='', prepend=True)

    audiowrapdir = bb.data.expand('${libdir}/imx-mm/audio-codec/wrap', d)
    do_split_packages(d, audiowrapdir, '^lib_(.*)_elinux\.so\..*',
                      aux_files_pattern_verbatim='${libdir}/imx-mm/audio-codec/wrap/lib_%s_elinux.so.*',
                      output_pattern='libfslcodec-audio-wrap-%s',
                      description='Freescale IMX Codec Wrap (%s)',
                      extra_depends='', prepend=True)

    videodir = bb.data.expand('${libdir}/imx-mm/video-codec', d)
    do_split_packages(d, videodir, '^lib_(.*)_elinux\.so\..*',
                      aux_files_pattern_verbatim='${libdir}/imx-mm/video-codec/lib_%s_elinux.so.*',
                      output_pattern='libfslcodec-video-%s',
                      description='Freescale IMX Codec (%s)',
                      extra_depends='', prepend=True)

    # FIXME: All binaries lack GNU_HASH in elf binary but as we don't have
    # the source we cannot fix it. Disable the insane check for now.
    for p in d.getVar('PACKAGES', True).split():
        d.setVar("INSANE_SKIP_%s" % p, "ldflags")
}

# Ensure we get warnings if we miss something
FILES_${PN} = ""

FILES_${PN}-dev += "${libdir}/imx-mm/*/*${SOLIBSDEV} \
                    ${libdir}/imx-mm/*/*/*${SOLIBSDEV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
