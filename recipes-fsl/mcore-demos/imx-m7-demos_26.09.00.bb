# Copyright 2026 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require imx-mcore-demos.inc

LIC_FILES_CHKSUM:mx8mn-nxp-bsp = "file://COPYING;md5=ef4f968b9dae8ba406390cd617c2301d"
LIC_FILES_CHKSUM:mx8mnul-nxp-bsp = "file://COPYING;md5=ef4f968b9dae8ba406390cd617c2301d"

LIC_FILES_CHKSUM:mx8mp-nxp-bsp = "file://COPYING;md5=ef4f968b9dae8ba406390cd617c2301d"
LIC_FILES_CHKSUM:mx8mpul-nxp-bsp = "file://COPYING;md5=ef4f968b9dae8ba406390cd617c2301d"

LIC_FILES_CHKSUM:mx95-nxp-bsp = "file://COPYING;md5=ef4f968b9dae8ba406390cd617c2301d"

LIC_FILES_CHKSUM:mx952-nxp-bsp = "file://COPYING;md5=ef4f968b9dae8ba406390cd617c2301d"

SRC_URI[imx8mn.sha256sum] = "d51f76ef7f3f47e35ed5103ee82d55811c57f9193f9b6228ea3d36bc06b48aea"
SRC_URI[imx8mnddr3l.sha256sum] = "9818553fcf490b3d4daf75fa41636d09f683289686cab8e5c11f53f6996cd3a1"

SRC_URI[imx8mp.sha256sum] = "ceeb458359b4b558f084f6197105007b940c60eb175bc5827ea92502bbbbc995"

SRC_URI[imx95.sha256sum] = "4fce1f91bb176467723841b99ec7d51d825d908720c7ae9f89fb9e839ece3fb8"

SRC_URI[imx952.sha256sum] = "e2aa93a55e8e5d00356c3c997d123effef5c1674ce6dc72fadbb4aa6735a9e4e"

COMPATIBLE_MACHINE = "(mx8mn-nxp-bsp|mx8mnul-nxp-bsp|mx8mp-nxp-bsp|mx8mpul-nxp-bsp|mx95-nxp-bsp|mx952-nxp-bsp)"
