# Use this class for U-Boot 2017.03 or older.

DEPENDS_append = " dtc-145-native"

do_configure_prepend () {
    sed -i -e 's/^DTC[[:space:]]*=[[:space:]]*dtc[[:space:]]*$/DTC = dtc-145/' ${S}/Makefile
}
