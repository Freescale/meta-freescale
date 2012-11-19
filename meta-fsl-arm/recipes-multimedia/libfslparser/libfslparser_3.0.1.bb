# Copyright (C) 2012 Freescale Semicondutors
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Freescale Multimedia parser libs"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://EULA.txt;md5=ea4d5c069d7aef0838a110409ea78a01"

inherit fsl-eula-unpack autotools pkgconfig

SRC_URI[md5sum] = "52a72680d4c1db4b9fadcae56980a8ae"
SRC_URI[sha256sum] = "50b514fe3e9912ac24e9da81de5e713bf42e1a7d3a41ae6f221258c2f8539194"
SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"

PACKAGES_DYNAMIC = "${PN}-*"

do_install_append() {
	# FIXME: The only parser outside imx-mm/parse is the mp3 so move it
	mv ${D}${libdir}/*mp3* ${D}${libdir}/imx-mm/parser/
}

python populate_packages_prepend() {
    parserdir = bb.data.expand('${libdir}/imx-mm/parser', d)
    do_split_packages(d, parserdir, '^lib_(.*)_elinux\.so\.',
                      aux_files_pattern_verbatim='${libdir}/imx-mm/parser/lib_%s_elinux.so.*',
                      output_pattern='libfslparser-%s', recursive=True,
                      description='Freescale IMX Parser (%s)',
                      extra_depends='', prepend=True)

    # FIXME: All binaries lack GNU_HASH in elf binary but as we don't have
    # the source we cannot fix it. Disable the insane check for now.
    for p in d.getVar('PACKAGES', True).split():
        d.setVar("INSANE_SKIP_%s" % p, "ldflags")
}

# Ensure we get warnings if we miss something
FILES_${PN} = ""

FILES_${PN}-dev += "${libdir}/imx-mm/*/*${SOLIBSDEV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
