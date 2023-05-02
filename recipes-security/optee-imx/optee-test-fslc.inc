# Copyright (C) 2017-2021 NXP

SUMMARY = "OPTEE test"
LICENSE = "BSD-2-Clause & GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=daa2bcccc666345ab8940aab1315a4fa"

require optee-fslc.inc

DEPENDS += "optee-os optee-client openssl"

EXTRA_OEMAKE += " \
    TA_DEV_KIT_DIR=${STAGING_INCDIR}/optee/export-user_ta_${OPTEE_ARCH}/ \
    CROSS_COMPILE_HOST=${HOST_PREFIX} \
    CROSS_COMPILE_TA=${HOST_PREFIX} \
    CROSS_COMPILE=${HOST_PREFIX} \
"

do_compile() {
    oe_runmake all
}
do_compile[cleandirs] = "${B}"

do_install () {
	install -d ${D}${bindir}
	install ${B}/xtest/xtest ${D}${bindir}

	install -d ${D}${nonarch_base_libdir}/optee_armtz
	find ${B}/ta -name '*.ta' | while read name; do
		install -m 444 $name ${D}${nonarch_base_libdir}/optee_armtz/
	done

	install -d ${D}${libdir}/tee-supplicant/plugins/
	install ${B}/supp_plugin/*plugin ${D}${libdir}/tee-supplicant/plugins/
}

FILES:${PN} += "${nonarch_base_libdir}/optee_armtz/ ${libdir}/tee-supplicant/plugins/"

RDEPENDS:${PN} = "optee-os"
