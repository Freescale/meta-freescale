TARGET_FPU = "hard"
DEFAULTTUNE ?= "ppc64e6500"

require conf/machine/include/powerpc/tune-ppce6500.inc
require conf/machine/include/qoriq-ppc.inc

MACHINEOVERRIDES =. "e6500-64b:"

# The 32-bit multilib is a distro or local choice: set in machine
# configuration it changes shared recipes' signatures. u-boot-qoriq
# needs it (U-Boot is 32-bit code) and skips itself without it. To
# enable it, add
#   require conf/multilib.conf
#   MULTILIBS = "multilib:lib32"
# to local.conf; this machine's lib32 tune is then:
DEFAULTTUNE:virtclass-multilib-lib32 ?= "ppce6500"
