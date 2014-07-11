TARGET_FPU = "hard"
DEFAULTTUNE ?= "ppc64e6500"

require conf/machine/include/tune-ppce6500.inc
require conf/machine/include/qoriq-base.inc

MACHINEOVERRIDES .= ":e6500-64b"
