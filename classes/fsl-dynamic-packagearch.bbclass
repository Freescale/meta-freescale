# Automatically set PACKAGE_ARCH for MACHINE_SOCARCH
#
# This allow to easy reuse of binary packages among similar SoCs. The
# usual use for this is to share SoC specific packages among different
# boards.
#
# MACHINE_SOCARCH_FILTER list all packages associated with
# MACHINE_SOCARCH and, when match, will set PACKAGE_ARCH as MACHINE_SOCARCH
#
# MACHINE_ARCH_FILTER list all packages associated with
# MACHINE_ARCH and, when match, will set PACKAGE_ARCH as MACHINE_ARCH
#
# For example, in meta-fsl-arm, this is used to share GPU packages for
# i.MX53 boards (as all them share the AMD GPU) and i.MX6 based boards
# (as all them share Vivante GPU).
#
# To use the class, specify, for example:
#
# MACHINE_SOCARCH_SUFFIX_soc = "-soc"
#
# and the need filters, as:
#
# MACHINE_ARCH_FILTER = "virtual/kernel"
# MACHINE_SOCARCH_FILTER_soc = "virtual/libgles1 ... virtual/libgl"
#
# Copyright 2013-2016 (C) O.S. Systems Software LTDA.

# Parse-time logic is required: PACKAGE_ARCH and PACKAGE_EXTRA_ARCHS are chosen
# by inspecting PROVIDES/DEPENDS against the SOCARCH filters and the multilib
# tunes, which cannot be expressed declaratively.
# nooelint: oelint.task.noanonpython
python __anonymous () {
    machine_arch_filter = set((d.getVar("MACHINE_ARCH_FILTER") or "").split())
    machine_socarch_filter = set((d.getVar("MACHINE_SOCARCH_FILTER") or "").split())
    if machine_socarch_filter or machine_arch_filter:
        provides = set((d.getVar("PROVIDES") or "").split())
        depends = set((d.getVar("DEPENDS") or "").split())
        PN = d.getVar("PN")

        package_arch = None
        if list(machine_arch_filter & (provides | depends)):
            package_arch = d.getVar("MACHINE_ARCH")
        elif list(machine_socarch_filter & (provides | depends)):
            package_arch = d.getVar("MACHINE_SOCARCH")
            if not package_arch:
                raise bb.parse.SkipPackage("You must set MACHINE_SOCARCH as MACHINE_SOCARCH_FILTER is set for this SoC.")

            machine_socarch_suffix = d.getVar("MACHINE_SOCARCH_SUFFIX")
            if not machine_socarch_suffix:
                raise bb.parse.SkipPackage("You must set MACHINE_SOCARCH_SUFFIX as MACHINE_SOCARCH_FILTER is set for this SoC.")

        if package_arch:
            bb.debug(1, "Use '%s' as package architecture for '%s'" % (package_arch, PN))
            d.setVar("PACKAGE_ARCH", package_arch)

    for arch in fsl_dynamic_packagearch_extra_archs(d):
        d.appendVar("PACKAGE_EXTRA_ARCHS", " %s" % arch)
}

MACHINE_SOCARCH = "${TUNE_PKGARCH}${MACHINE_SOCARCH_SUFFIX}"

ARM_EXTRA_SOCARCH = "${ARMPKGARCH}${ARMPKGSFX_DSP}${ARMPKGSFX_EABI}${ARMPKGSFX_ENDIAN}${ARMPKGSFX_FPU}${MACHINE_SOCARCH_SUFFIX}"
THUMB_EXTRA_SOCARCH = "${ARMPKGARCH}${ARM_THUMB_SUFFIX}${ARMPKGSFX_DSP}${ARMPKGSFX_EABI}${ARMPKGSFX_ENDIAN}${ARMPKGSFX_FPU}${MACHINE_SOCARCH_SUFFIX}"

# The SoC architectures are added per recipe, so list them here too: OE-core
# only removes stale sysroot manifests for the architectures in SSTATE_ARCHS.
SSTATE_ARCHS:append = " ${@fsl_dynamic_packagearch_sstate_archs(d)}"

# The SoC architectures to add to PACKAGE_EXTRA_ARCHS, in order
def fsl_dynamic_packagearch_extra_archs(d):
    cur_package_archs = (d.getVar("PACKAGE_ARCHS") or "").split()
    archs = []

    machine_socarch = (d.getVar("MACHINE_SOCARCH") or "")
    if not machine_socarch in cur_package_archs:
        archs.append(machine_socarch)

    multilib_variants = (d.getVar("MULTILIB_VARIANTS") or "").split()
    for variant in multilib_variants:
        defaulttune = d.getVar("DEFAULTTUNE:virtclass-multilib-" + variant)
        if defaulttune:
            package_extra_archs_tune_archs = (d.getVar("PACKAGE_EXTRA_ARCHS:tune-" + defaulttune) or "").split()
            arch_suffix = d.getVar("MACHINE_SOCARCH_SUFFIX")
            for arch in package_extra_archs_tune_archs:
                socarch = arch + arch_suffix
                if not socarch in cur_package_archs:
                    archs.append(socarch)

    if d.getVar("TUNE_ARCH") == "arm":
        # For ARM we have two possible machine_socarch values, one for the arm and one for the thumb instruction set
        # add the other value to extra archs also, so that a image recipe searches both for packages.
        if  d.getVar("ARM_INSTRUCTION_SET") == "thumb":
            archs.append(d.getVar("ARM_EXTRA_SOCARCH"))
        else:
            archs.append(d.getVar("THUMB_EXTRA_SOCARCH"))

    return archs

def fsl_dynamic_packagearch_sstate_archs(d):
    # Within a recipe the anonymous function has already added them
    cur_package_archs = (d.getVar("PACKAGE_ARCHS") or "").split()
    archs = fsl_dynamic_packagearch_extra_archs(d)
    return " ".join(arch for arch in dict.fromkeys(archs) if arch and arch not in cur_package_archs)
