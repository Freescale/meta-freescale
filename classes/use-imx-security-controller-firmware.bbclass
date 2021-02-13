#
# Class to provide a possibility to re-use names of Security Controller (SECO)
# Firmware files, which are required by certain derivatives on i.MX8 family.
#
# SECO Firmware names are used by imx-boot and imx-seco recipes, therefore
# their respective definitions are moved into a separate class, which is
# inherited by both recipes.
#
# Currently, only 'mx8m' family does not require SECO FW to be provided in the
# target image, therefore as a first step - the machine override is verified
# if it matches, and then derivative is taken.
#
# NOTE: SECO_FIRMWARE_NAME defaults to empty string, and is verified against
# the family first. If a derivative in the family does not have a firmware name
# set in this class - recipe parsing is stopped.
# This behavior ensures that derivatives which requires SECO Firmware to be
# present in the image file have it properly defined.

SECO_FIRMWARE_NAME              ?= ""

SECO_FIRMWARE_NAME_mx8qm         = "mx8qmb0-ahab-container.img"
SECO_FIRMWARE_NAME_mx8qxp        = \
    "${@bb.utils.contains('MACHINE_FEATURES', 'soc-revb0', 'mx8qxb0-ahab-container.img', \
                                                           'mx8qxc0-ahab-container.img', d)}"
SECO_FIRMWARE_NAME_mx8phantomdxl = "mx8qxc0-ahab-container.img"
SECO_FIRMWARE_NAME_mx8dxl        = "mx8dxla1-ahab-container.img"

python () {
    if "mx8m" in d.getVar('MACHINEOVERRIDES').split(":"):
        return # We need to allow the recipes to be parsed for this case

    seco_firmware = d.getVar('SECO_FIRMWARE_NAME')
    if not seco_firmware:
        raise bb.parse.SkipRecipe("This SoC requires 'SECO_FIRMWARE_NAME', define it in 'use-imx-security-controller-firmware' bbclass")
}

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
