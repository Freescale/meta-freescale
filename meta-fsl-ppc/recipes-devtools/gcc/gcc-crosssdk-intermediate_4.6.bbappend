require gcc-fsl.inc

do_install () {
        oe_runmake 'DESTDIR=${D}' install
        install -d ${D}${target_base_libdir}/
        cp -rf ${D}${exec_prefix}/${TARGET_SYS}/${baselib}/ ${D}${target_base_libdir}/

        # We don't really need this (here shares/ contains man/, info/, locale/).
        rm -rf ${D}${datadir}/

        # We use libiberty from binutils
        find ${D}${exec_prefix}/lib -name libiberty.a | xargs rm -f
        find ${D}${exec_prefix}/lib -name libiberty.h | xargs rm -f

        # Insert symlinks into libexec so when tools without a prefix are searched for, the correct ones are
        # found. These need to be relative paths so they work in different locations.
        dest=${D}${libexecdir}/gcc/${TARGET_SYS}/${BINV}/
        install -d $dest
        for t in ar as ld nm objcopy objdump ranlib strip g77 gcc cpp gfortran; do
               ln -sf ${BINRELPATH}/${TARGET_PREFIX}$t $dest$t
        done
}
