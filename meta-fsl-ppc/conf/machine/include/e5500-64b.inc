TARGET_FPU = "hard"
DEFAULTTUNE ?= "ppc64e5500"

require conf/machine/include/tune-ppce5500.inc
require conf/machine/include/qoriq-base.inc

MACHINEOVERRIDES .= ":e5500-64b:fslmachine"
