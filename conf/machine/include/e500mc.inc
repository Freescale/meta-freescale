TARGET_FPU = "hard"

require conf/machine/include/tune-ppce500mc.inc

MACHINEOVERRIDES =. "e500mc:"
require conf/machine/include/qoriq-ppc.inc
