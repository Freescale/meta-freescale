TARGET_FPU = "hard"

require conf/machine/include/tune-ppce500mc.inc
require conf/machine/include/qoriq-base.inc

MACHINEOVERRIDES .= ":e500mc"
