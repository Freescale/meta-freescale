TARGET_FPU = "hard"

require conf/machine/include/powerpc/tune-ppce6500.inc
require conf/machine/include/qoriq-ppc.inc

MACHINEOVERRIDES =. "e6500:"

BUILD_64BIT_KERNEL = "1"

require conf/multilib.conf
MULTILIBS ?= "multilib:lib64"
DEFAULTTUNE:virtclass-multilib-lib64 ?= "ppc64e6500"
