SUMMARY = "Linux Kernel for Freescale layerscape platforms"
DESCRIPTION = "Linux Kernel provided and supported by Freescale with focus on \
Layerscape1 Family Boards. "

require recipes-kernel/linux/linux-ls1.inc
require recipes-kernel/linux/linux-dtb.inc

SRCBRANCH = "master"
SRCREV = "f488de6741d5ba805b9fe813d2ddf32368d3a888"

COMPATIBLE_MACHINE = "(ls102xa)"
