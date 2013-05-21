# Copyright (C) 2012-2013 Freescale Semicondutors
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Freescale Multimedia codec libs"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://EULA.txt;md5=ea4d5c069d7aef0838a110409ea78a01"

INC_PR = "r1"

inherit fsl-eula-unpack autotools pkgconfig

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"

PACKAGES_DYNAMIC = "${PN}-*"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

PACKAGES += "${PN}-meta"

ALLOW_EMPTY_${PN} = "1"
ALLOW_EMPTY_${PN}-meta = "1"

do_install_append() {
	# FIXME: This link points to nowhere
	rm ${D}${libdir}/imx-mm/audio-codec/lib_src_ppp_arm11_elinux.so

    # LTIB move the files around or gst-fsl-plugin won't find them
    for p in $(find ${D}${libdir}/imx-mm -mindepth 2 -maxdepth 2 -not -type d); do
            mv $p ${D}${libdir}
    done
    rmdir ${D}${libdir}/imx-mm/video-codec

	# FIXME: Drop examples
	rm -r ${D}${datadir}/imx-mm
}

python populate_packages_prepend() {
    codecdir = bb.data.expand('${libdir}', d)
    do_split_packages(d, codecdir, '^lib_([^_]*).*_arm.*_elinux\.so\..*',
                      aux_files_pattern='${libdir}/imx-mm/audio-codec/wrap/lib_%sd_wrap_arm*_elinux.so.*',
                      output_pattern='libfslcodec-%s',
                      description='Freescale i.MX Codec (%s)',
                      extra_depends='')

    # FIXME: All binaries lack GNU_HASH in elf binary but as we don't have
    # the source we cannot fix it. Disable the insane check for now.
    for p in d.getVar('PACKAGES', True).split():
        d.setVar("INSANE_SKIP_%s" % p, "ldflags textrel")
        d.setVar("DEBIAN_NOAUTONAME_%s" % p, "1")

    # Allow addition of all codecs in a image; useful specially for
    # debugging.
    codec_pkgs = oe.utils.packages_filter_out_system(d)
    d.appendVar('RDEPENDS_libfslcodec-meta', ' ' + ' '.join(codec_pkgs))
}

# Ensure we get warnings if we miss something
FILES_${PN} = ""

FILES_${PN}-dev += "${libdir}/imx-mm/*/*${SOLIBSDEV} \
                    ${libdir}/imx-mm/*/*/*${SOLIBSDEV}"

# FIXME: The wrap and lib names does not match
FILES_${PN}-oggvorbis += "${libdir}/imx-mm/audio-codec/wrap/lib_vorbisd_wrap_arm*_elinux.so.*"

PACKAGE_ARCH = "${MACHINE_ARCH}"
