# Copyright (C) 2012-2018 O.S. Systems Software LTDA.
# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright 2017-2021 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale Multimedia parser libs"
LICENSE = "Proprietary"
SECTION = "multimedia"

# FIX 1: License Checksums
# - EULA: Points to the file we will copy into S (see do_patch below).
# - COPYING: Points to the existing source license file.
LIC_FILES_CHKSUM = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"

# For backwards compatibility
PROVIDES += "libfslparser"
RREPLACES:${PN} = "libfslparser"
RPROVIDES:${PN} = "libfslparser"
RCONFLICTS:${PN} = "libfslparser"

SRC_URI = "${FSL_MIRROR}/${BP}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true"

# FIX 2: Correct Source Path for Whinlatter/Master Compliance
S = "${UNPACKDIR}/${BP}-${IMX_SRCREV_ABBREV}"

inherit fsl-eula-unpack autotools pkgconfig

# Choose between 32-bit and 64-bit binaries and between Soft Float-Point and Hard Float-Point
EXTRA_OECONF = "${@bb.utils.contains('TUNE_FEATURES', 'aarch64', '--enable-armv8', \
                  bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', '--enable-fhw', '--enable-fsw', d), d)}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

python __set_insane_skip() {
    # FIXME: All binaries lack GNU_HASH in elf binary but as we don't have
    # the source we cannot fix it. Disable the insane check for now.
    for p in d.getVar('PACKAGES').split():
        d.setVar("INSANE_SKIP:%s" % p, "ldflags dev-so textrel")
}

do_package_qa[prefuncs] += "__set_insane_skip"

# FIXME: gst-fsl-plugin looks for the .so files so we need to deploy those
FILES:${PN} += "${libdir}/imx-mm/*/*${SOLIBS} ${libdir}/imx-mm/*/*${SOLIBSDEV}"

INHIBIT_SYSROOT_STRIP = "1"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"

# FIX 3: Copy the EULA from the meta-layer to the source directory
# The binary doesn't produce 'EULA', so we copy it manually from the layer files
# to ${S} so that do_populate_lic can find it.
python do_patch:prepend() {
    import shutil
    import os

    # Get the location of the master EULA file in the meta-layer
    fsl_eula_src = d.getVar("FSL_EULA_FILE")
    s_dir = d.getVar("S")
    eula_dst = os.path.join(s_dir, "EULA")

    # If FSL_EULA_FILE variable works (standard case)
    if fsl_eula_src and os.path.exists(fsl_eula_src):
        bb.note(f"Copying master EULA from {fsl_eula_src} to {eula_dst}")
        shutil.copy2(fsl_eula_src, eula_dst)
    else:
        # Fallback: Try to find it in WORKDIR (some classes unpack it there)
        work_dir = d.getVar("WORKDIR")
        eula_fallback = os.path.join(work_dir, "EULA")
        if os.path.exists(eula_fallback):
             shutil.copy2(eula_fallback, eula_dst)
        else:
             bb.warn(f"Could not locate EULA at {fsl_eula_src} or {eula_fallback}")
}
