include fsl-mm-codeclib.inc

PR = "${INC_PR}.0"

LIC_FILES_CHKSUM = "file://docs/EULA.txt;md5=ea4d5c069d7aef0838a110409ea78a01"

SRC_URI[md5sum] = "9ee06c147f635d1f904d53fc193150cc"
SRC_URI[sha256sum] = "427436c0ab7795204a23150bac19cf261a88693e9acd9d3eab5c06c60ffe55bd"

do_install_append() {
    # FIXME: Same pkgconfig file is provided in every source package
    #        so we install it just here and all other packages need to
    #        depends on fsl-mm-codeclib-dev explicitly.
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${S}/pkgconfig/fsl-mm-core.pc ${D}${libdir}/pkgconfig
}

# FIXME: Install files which has no sonames
PACKAGES = "${PN} ${PN}-dev"
FILES_${PN} += " \
    ${libdir}/lib_aac_parser_arm11_elinux.so \
    ${libdir}/lib_bmp_dec_arm11_elinux.so \
    ${libdir}/lib_deinterlace_arm11_elinux.so \
    ${libdir}/lib_flac_parser_arm11_elinux.so \
    ${libdir}/lib_gif_dec_arm11_elinux.so \
    ${libdir}/lib_id3_parser_arm11_elinux.so \
    ${libdir}/lib_jpeg_enc_arm11_elinux.so \
    ${libdir}/lib_mp3_parser_v2_arm11_elinux.so \
    ${libdir}/lib_png_dec_arm11_elinux.so \
    ${libdir}/lib_src_ppp_arm11_elinux.so \
    ${libdir}/lib_wav_parser_arm11_elinux.so \
    ${libdir}/libmpeg4_encoder_arm11_ELINUX.so \
"

COMPATIBLE_MACHINE = "(mx5)"
