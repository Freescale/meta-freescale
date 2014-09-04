SUMMARY = "Linux Kernel for Freescale layerscape platforms"
DESCRIPTION = "Linux Kernel provided and supported by Freescale with focus on \
Layerscape1 Family Boards. "

require recipes-kernel/linux/linux-ls1.inc
require recipes-kernel/linux/linux-dtb.inc

SRCBRANCH = "ls1-dev"
SRCREV = "de1cb4b3c16be38cf3981fd0afa143ad24283d07"

COMPATIBLE_MACHINE = "(ls102xa)"
