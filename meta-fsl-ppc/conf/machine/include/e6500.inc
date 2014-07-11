TARGET_FPU = "hard"

require conf/machine/include/tune-ppce6500.inc
require conf/machine/include/qoriq-base.inc

MACHINEOVERRIDES .= ":e6500"

BUILD_64BIT_KERNEL = "1"

