TARGET_FPU = "hard"

require conf/machine/include/tune-ppce5500.inc

MACHINEOVERRIDES =. "e5500:"
require conf/machine/include/qoriq-ppc.inc
