TARGET_FPU = "hard"

require conf/machine/include/tune-ppce6500.inc

MACHINEOVERRIDES =. "e6500:"
require conf/machine/include/qoriq-ppc.inc

BUILD_64BIT_KERNEL = "1"

require conf/multilib.conf
MULTILIBS ?= "multilib:lib64"
DEFAULTTUNE_virtclass-multilib-lib64 ?= "ppc64e6500"
