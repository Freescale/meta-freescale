# Copyright (C) 2011, 2012 Freescale
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "GPU driver and apps for frambuffer on mx51"

include amd-gpu-mx51.inc

SRC_URI[md5sum] = "9f9b5f67b595721a08793aae8bd8fc46"
SRC_URI[sha256sum] = "f0db68a764b5fb199729e7435f606b8d12b61ca97990336c647b7e81f4a584d9"

RCONFLICTS_${PN} = "amd-gpu-x11-bin-mx51"

COMPATIBLE_MACHINE = "(mx5)"
