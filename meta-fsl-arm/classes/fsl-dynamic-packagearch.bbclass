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
# MACHINE_SOCARCH_soc = "${TUNE_PKGARCH}-soc"
#
# and the need filters, as:
#
# MACHINE_ARCH_FILTER = "virtual/kernel"
# MACHINE_SOCARCH_FILTER_soc = "virtual/libgles1 ... virtual/libgl"
#
# Copyright 2013 (C) O.S. Systems Software LTDA.

python __anonymous () {
    machine_arch_filter = set((d.getVar("MACHINE_ARCH_FILTER", True) or "").split())
    machine_socarch_filter = set((d.getVar("MACHINE_SOCARCH_FILTER", True) or "").split())
    if machine_socarch_filter or machine_arch_filter:
        provides = set((d.getVar("PROVIDES", True) or "").split())
        depends = set((d.getVar("DEPENDS", True) or "").split())
        PN = d.getVar("PN", True)

        package_arch = None
        if list(machine_arch_filter & (provides | depends)):
            package_arch = d.getVar("MACHINE_ARCH", True)
        elif list(machine_socarch_filter & (provides | depends)):
            package_arch = d.getVar("MACHINE_SOCARCH", True)
            if not package_arch:
                bb.parse.SkipPackage("You must set MACHINE_SOCARCH as MACHINE_SOCARCH_FILTER is set for this SoC.")

        if package_arch:
            bb.debug(1, "Use '%s' as package archictecture for '%s'" % (package_arch, PN))
            d.setVar("PACKAGE_ARCH", package_arch)
}
