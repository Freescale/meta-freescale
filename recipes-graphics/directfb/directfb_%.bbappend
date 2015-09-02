# i.MX6 has DirectFB acceleration support, so add it
RDEPENDS_${PN}_append_mx6 = " libvivante-dfb-mx6"
PACKAGE_ARCH_mx6 = "${MACHINE_ARCH}"
