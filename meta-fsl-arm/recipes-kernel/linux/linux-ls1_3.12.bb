SUMMARY = "Linux Kernel for Freescale layerscape platforms"
DESCRIPTION = "Linux Kernel provided and supported by Freescale with focus on \
Layerscape1 Family Boards. "

require recipes-kernel/linux/linux-ls1.inc
require recipes-kernel/linux/linux-dtb.inc

SRCBRANCH = "sdk-v1.7.x"
SRCREV = "6619b8b55796cdf0cec04b66a71288edd3057229"

COMPATIBLE_MACHINE = "(ls102xa)"
