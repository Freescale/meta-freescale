SUMMARY = "Linux Kernel for Freescale layerscape platforms"
DESCRIPTION = "Linux Kernel provided and supported by Freescale with focus on \
Layerscape1 Family Boards. "

require recipes-kernel/linux/linux-ls1.inc
require recipes-kernel/linux/linux-dtb.inc

SRCBRANCH = "ls1-dev"
SRCREV = "3ced9e82f9f3f7b232bea0bd18c9a62572c06b44"

COMPATIBLE_MACHINE = "(ls102xa)"
