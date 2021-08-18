TARGET_FPU = "hard"
DEFAULTTUNE ?= "ppc64e5500"

require conf/machine/include/powerpc/tune-ppce5500.inc
require conf/machine/include/qoriq-ppc.inc

MACHINEOVERRIDES =. "e5500-64b:"

require conf/multilib.conf
MULTILIBS ?= "multilib:lib32"
DEFAULTTUNE:virtclass-multilib-lib32 ?= "ppce5500"
