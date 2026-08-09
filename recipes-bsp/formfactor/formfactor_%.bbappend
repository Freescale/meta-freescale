# Append path for i.MX custom matchconfig
# Standard bbappend idiom: cannot carry an override, and FILESEXTRAPATHS is
# in BB_BASEHASH_IGNORE_VARS, so it cannot reach a task signature.
# nooelint: oelint.vars.noncoreoverride
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
