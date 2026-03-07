# Copyright 2023-2025 NXP

DESCRIPTION = "NXP Multimedia opencl converter lib"
LICENSE = "Proprietary"
SECTION = "multimedia"

# FIX 1: License Checksums
# - EULA: Points to the file we will copy into S (see do_patch below).
# - COPYING: Points to the existing source license file.
LIC_FILES_CHKSUM = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"

DEPENDS = "imx-gpu-viv"

SRC_URI = "${FSL_MIRROR}/${BP}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true"
IMX_SRCREV_ABBREV = "00ddb57"

SRC_URI[sha256sum] = "b4e0b0c3418d5d73833bc9cc4eb299da854d2218e217bc23b922832396cd6451"

S = "${UNPACKDIR}/${BP}-${IMX_SRCREV_ABBREV}"

inherit fsl-eula-unpack autotools pkgconfig meson

FILES:${PN} += "${datadir}/"
COMPATIBLE_MACHINE             = "(^$)"
COMPATIBLE_MACHINE:imxgpu      = "(mx8-nxp-bsp|mx95-nxp-bsp)"
COMPATIBLE_MACHINE:mx8mm-nxp-bsp = "(^$)"

# FIX 2: Copy the EULA from the meta-layer to the source directory
# The binary doesn't produce 'EULA', but meta-freescale has it.
# We copy it to ${S} so do_populate_lic can find it.
python do_patch:prepend() {
    import shutil
    import os

    # Get the location of the master EULA file in the meta-layer
    fsl_eula_src = d.getVar("FSL_EULA_FILE")
    s_dir = d.getVar("S")
    eula_dst = os.path.join(s_dir, "EULA")

    if fsl_eula_src and os.path.exists(fsl_eula_src):
        bb.note(f"Copying master EULA from {fsl_eula_src} to {eula_dst}")
        shutil.copy2(fsl_eula_src, eula_dst)
    else:
        # Fallback: Try to find it in WORKDIR if FSL_EULA_FILE is somehow empty
        work_dir = d.getVar("WORKDIR")
        eula_fallback = os.path.join(work_dir, "EULA")
        if os.path.exists(eula_fallback):
             shutil.copy2(eula_fallback, eula_dst)
        else:
             bb.warn(f"Could not locate FSL_EULA_FILE at {fsl_eula_src}")
}
