# i.MX6 has DirectFB acceleration support, so add it
RDEPENDS_${PN}_append_mx6q  = " libvivante-dfb-mx6"
PACKAGE_ARCH_mx6q  = "${MACHINE_ARCH}"

RDEPENDS_${PN}_append_mx6dl = " libvivante-dfb-mx6"
PACKAGE_ARCH_mx6dl = "${MACHINE_ARCH}"

RDEPENDS_${PN}_append_mx6sx = " libvivante-dfb-mx6"
PACKAGE_ARCH_mx6sx = "${MACHINE_ARCH}"

RDEPENDS_${PN}_append_mx6sl = " libvivante-dfb-mx6"
PACKAGE_ARCH_mx6sl = "${MACHINE_ARCH}"
