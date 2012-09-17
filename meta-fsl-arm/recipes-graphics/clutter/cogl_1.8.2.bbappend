PRINC := "${@int(PRINC) + 1}"

EXTRA_OECONF_mx5 = "${BASE_CONF} --with-flavour=glx --enable-gles2 --disable-gl"
PACKAGE_ARCH_mx5 = "${MACHINE_ARCH}"
