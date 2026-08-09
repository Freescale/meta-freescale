# Prepend path to override files from upstream recipe
# Standard bbappend idiom: cannot carry an override, and FILESEXTRAPATHS is
# in BB_BASEHASH_IGNORE_VARS, so it cannot reach a task signature.
# nooelint: oelint.vars.noncoreoverride
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_ARCH:imx-generic-bsp = "${MACHINE_ARCH}"
PACKAGE_ARCH:qoriq-generic-bsp = "${MACHINE_ARCH}"
