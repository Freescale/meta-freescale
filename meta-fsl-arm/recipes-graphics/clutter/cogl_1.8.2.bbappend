PRINC := "${@int(PRINC) + 2}"

DEPENDS_mx5 = "${STDDEPENDS} virtual/libgles2 libxcomposite libxdamage"

EXTRA_OECONF_mx5 = "${BASE_CONF} --with-flavour=glx --enable-gles2 --disable-gl"
PACKAGE_ARCH_mx5 = "${MACHINE_ARCH}"
