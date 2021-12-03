# So far, we just need a container to be inherited
inherit ${@oe.utils.ifelse(d.getVar('UBOOT_PROVIDES_BOOT_CONTAINER') == '1', 'imx-boot-container', '')}

# Location known to imx-boot component, where U-Boot artifacts
# should be additionally deployed.
# See below note above do_deploy:append:mx8m for the purpose of
# this delopyment location
BOOT_TOOLS = "imx-boot-tools"
