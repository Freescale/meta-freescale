# Fixup for the libubootenv which rely on uboot-config class for no good reason.
#
# This is not intended to be permanent but we need to get the integration
# working and there is no good solution for now so we are adding this in a
# non-intrusive way and using the `IMX_DEFAULT_BOOTLOADER` as a guard to do any
# code execution.

def fixup_uboot_config_dependency(d):
    ubootmachine = d.getVar("UBOOT_MACHINE")
    ubootconfig = (d.getVar('UBOOT_CONFIG') or "").split()
    imx_default_bootloader = d.get('IMX_DEFAULT_BOOTLOADER')

    if not ubootmachine and not ubootconfig and imx_default_bootloader:
       # FIXME: We need to provide the UBOOT_MACHINE or UBOOT_CONFIG to allow libubootenv to
       # build. This is caused by the commit below:
       #
       # ,----[ libubootenv change ]
       # | commit 10aa1291979fb90bed1beb49be4d406ed0e1e4d5 ┃
       # | ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━
       # | Author: Ming Liu <liu.ming50@gmail.com>
       # | Date:   Tue Aug 25 20:08:01 2020 +0200
       # |
       # |     libubootenv: inherit uboot-config
       # |
       # |     This mainly aims to involve in the sanity check of UBOOT_CONFIG and
       # |     UBOOT_MACHINE, it will throw a error message at recipe parsing time if
       # |     neither of them is set, and libubootenv would be skipped.
       # |
       # |     Signed-off-by: Ming Liu <liu.ming50@gmail.com>
       # |     Signed-off-by: Richard Purdie <richard.purdie@linuxfoundation.org>
       # `----
       ubootmachine = d.getVar("UBOOT_MACHINE:pn-%s" % imx_default_bootloader)
       ubootconfig = (d.getVar("UBOOT_CONFIG:pn-%s" % imx_default_bootloader) or "").split()

       d.setVar("UBOOT_CONFIG", ubootconfig)
       d.setVar("UBOOT_MACHINE", ubootmachine)

python fixup_uboot_config_dependency_handler() {
    fixup_uboot_config_dependency(d)
}

fixup_uboot_config_dependency_handler[eventmask] = "bb.event.RecipePreFinalise"
addhandler fixup_uboot_config_dependency_handler
