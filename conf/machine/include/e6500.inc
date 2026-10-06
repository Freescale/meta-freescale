TARGET_FPU = "hard"

require conf/machine/include/powerpc/tune-ppce6500.inc
require conf/machine/include/qoriq-ppc.inc

MACHINEOVERRIDES =. "e6500:"

BUILD_64BIT_KERNEL = "1"

