# i.MX customization for mesa_26.2.0.imx.bb.
# Not a standalone recipe: required by that .bb, and must stay beside
# it. Keeping it separate lets the .bb remain a verbatim copy of the
# OE-core original.

SUMMARY = "A free implementation of the OpenGL API, NXP i.MX fork"

require mesa-26.2.0.imx-source.inc

SRC_URI += "\
    file://mesa-panthor.conf \
    file://panthor.conf \
    file://rusticl.sh"

# Panfrost is the Mesa driver for the Mali GPU. Its Gallium and Vulkan
# drivers are only built with libclc.
PACKAGECONFIG:append:imxmali = " panfrost libclc"

# Mesa drives the GPU with the panthor kernel module, the Mali DDK with
# mali_kbase. Only one can be loaded. When the Mali DDK is not installed,
# load panthor here; in dual-driver mode, mali-imx configures it.
MESA_IMX_PANTHOR_ONLY = "${@'0' if 'mali-imx' in (d.getVar('IMX_MALI_DRIVERS') or '').split() else '1'}"

do_install:append() {
    if ${@bb.utils.contains('PACKAGECONFIG', 'panfrost', 'true', 'false', d)}; then
        # Install a system-wide environment snippet to auto-enable Rusticl on Panfrost GPUs.
        # This affects login shells and most service environments that source /etc/profile.d.
        install -d ${D}${sysconfdir}/profile.d
        install -m 0644 ${UNPACKDIR}/rusticl.sh ${D}${sysconfdir}/profile.d/
        if ${@bb.utils.contains('PACKAGECONFIG', 'vulkan', 'true', 'false', d)}; then
            # For Vulkan, the lavapipe renderer lvp_icd.json is the default because it is
            # alphabetically before panfrost_icd.json. Make panfrost the default.
            cd ${D}${datadir}/vulkan/icd.d/
            for i in $(ls); do
                case $i in
                panfrost_icd.json)
                    mv $i 10-$i
                    ;;
                *)
                    mv $i 90-$i
                    ;;
                esac
            done
            cd -
        fi

        if [ "${MESA_IMX_PANTHOR_ONLY}" = "1" ]; then
            install -d ${D}${sysconfdir}/modprobe.d ${D}${nonarch_libdir}/modules-load.d
            install -m 0644 ${UNPACKDIR}/mesa-panthor.conf ${D}${sysconfdir}/modprobe.d/
            install -m 0644 ${UNPACKDIR}/panthor.conf ${D}${nonarch_libdir}/modules-load.d/
        fi
    fi
}

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

# The fork is only meant for the i.MX Mali GPU. Not set for
# mesa-tools-native, as native recipes do not see machine overrides; it is
# selected with PREFERRED_VERSION on the Mali machines instead.
COMPATIBLE_MACHINE = "(imxmali)"

# The packages below are defined in OE-core's mesa.inc, which oelint cannot
# read (see oelint.constants.json), so it does not know them.
# nooelint: oelint.vars.specific,oelint.vars.pkgspecific.FILES
FILES:libopencl-mesa += "${sysconfdir}/profile.d/rusticl.sh"
# nooelint: oelint.vars.specific,oelint.vars.pkgspecific.FILES
FILES:mesa-megadriver += "\
    ${sysconfdir}/modprobe.d/mesa-panthor.conf \
    ${nonarch_libdir}/modules-load.d/panthor.conf"

# In dual-driver mode, the Mesa and Mali DDK libraries are installed side by
# side, so each Mesa package pulls in its Mali DDK counterpart.
# nooelint: oelint.vars.specific,oelint.vars.pkgspecific.RDEPENDS
RDEPENDS:libegl-mesa:append:imxmali = " ${@oe.utils.conditional('IMX_MALI_DUAL_DRIVER', '1', 'mali-imx-libegl', '', d)}"
# nooelint: oelint.vars.specific,oelint.vars.pkgspecific.RDEPENDS
RDEPENDS:libgles1-mesa:append:imxmali = " ${@oe.utils.conditional('IMX_MALI_DUAL_DRIVER', '1', 'mali-imx-libgles1', '', d)}"
# nooelint: oelint.vars.specific,oelint.vars.pkgspecific.RDEPENDS
RDEPENDS:libgles2-mesa:append:imxmali = " ${@oe.utils.conditional('IMX_MALI_DUAL_DRIVER', '1', 'mali-imx-libgles2', '', d)}"
# nooelint: oelint.vars.specific,oelint.vars.pkgspecific.RDEPENDS
RDEPENDS:libgbm:append:imxmali = " ${@oe.utils.conditional('IMX_MALI_DUAL_DRIVER', '1', 'mali-imx-libgbm', '', d)}"
# nooelint: oelint.vars.specific,oelint.vars.pkgspecific.RDEPENDS
RDEPENDS:libopencl-mesa:append:imxmali = " ${@oe.utils.conditional('IMX_MALI_DUAL_DRIVER', '1', 'mali-imx-opencl-icd', '', d)}"
# nooelint: oelint.vars.specific,oelint.vars.pkgspecific.RDEPENDS
RDEPENDS:mesa-vulkan-drivers:append:imxmali = " ${@oe.utils.conditional('IMX_MALI_DUAL_DRIVER', '1', 'mali-imx-libvulkan', '', d)}"

# In dual-driver mode, recommend both OpenCL and both Vulkan ICDs
# nooelint: oelint.vars.specific,oelint.vars.pkgspecific.RRECOMMENDS
RRECOMMENDS:mesa-megadriver:append:imxmali = " ${@oe.utils.conditional('IMX_MALI_DUAL_DRIVER', '1', 'libopencl-mesa mali-imx-opencl-icd mesa-vulkan-drivers mali-imx-libvulkan', '', d)}"
