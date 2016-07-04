# Freescale Kernel Vivante Kernel Driver handler
#
# Enable the kernel to provide or not the Vivante kernel driver and
#  dynamically set the proper providers per machine.
#
# The following options are supported:
#
#  MACHINE_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT
#
#     Machine does or does not have support for the Vivante kernel
#     driver, options are:
#
#       0 - machine does not have Vivante GPU driver support
#       1 - machine has Vivante GPU driver support
#
#  MACHINE_USES_VIVANTE_KERNEL_DRIVER_MODULE
#
#      Machine uses the Vivante kernel driver as module, options are:
#
#       0 - enable the builtin kernel driver module
#       1 - enable the external kernel module
#
# Copyright 2015, 2016 (C) O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

# Handle Vivante kernel driver setting:
#   0 - machine does not have Vivante GPU driver support
#   1 - machine has Vivante GPU driver support
MACHINE_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT ??= "0"

# Use Vivante kernel driver module:
#   0 - enable the builtin kernel driver module
#   1 - enable the external kernel module
MACHINE_USES_VIVANTE_KERNEL_DRIVER_MODULE ??= "${@d.getVar('MACHINE_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT', False) or '0'}"

python fsl_vivante_kernel_driver_handler () {
    has_vivante_kernel_driver_support = e.data.getVar('MACHINE_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT', True) or "0"
    use_vivante_kernel_driver_module = e.data.getVar('MACHINE_USES_VIVANTE_KERNEL_DRIVER_MODULE', True) or "0"

    if has_vivante_kernel_driver_support != "1":
        return

    if use_vivante_kernel_driver_module != "1":
        e.data.appendVar('RPROVIDES_kernel-base', ' kernel-module-imx-gpu-viv')
        e.data.appendVar('RREPLACES_kernel-base', ' kernel-module-imx-gpu-viv')
        e.data.appendVar('RCONFLICTS_kernel-base', ' kernel-module-imx-gpu-viv')
}

addhandler fsl_vivante_kernel_driver_handler
fsl_vivante_kernel_driver_handler[eventmask] = "bb.event.RecipePreFinalise"

do_configure_append () {
    if [ "${MACHINE_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT}" = "1" ]; then
        config="${B}/.config"

        sed -i "/CONFIG_MXC_GPU_VIV[ =]/d" $config
        if [ "${MACHINE_USES_VIVANTE_KERNEL_DRIVER_MODULE}" = "1" ]; then
            echo "# CONFIG_MXC_GPU_VIV is not set" >> $config
        else
            echo "CONFIG_MXC_GPU_VIV=y" >> $config
        fi
    fi
}
