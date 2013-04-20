require recipes-graphics/xorg-xserver/xserver-xorg.inc

# This has change in newer release
LIC_FILES_CHKSUM = "file://COPYING;md5=74df27b6254cc88d2799b5f4f5949c00"

# Misc build failure for master HEAD
SRC_URI += "file://crosscompile.patch \
            file://fix_open_max_preprocessor_error.patch \
            file://mips64-compiler.patch \
            file://pkgconfig-deps.patch \
           "

SRC_URI[md5sum] = "256325e9b17dff479d92bed97f6b0adb"
SRC_URI[sha256sum] = "3e2935bc400612df58d5b5e6840829e2c63af02c2e7d2893092500358a4366fc"

PR = "${INC_PR}.0"

do_install_append_mx6 () {
    # FIXME: This is a workaround to ensure we use Vivante DRI
    rm ${D}${includedir}/xorg/dri.h \
       ${D}${includedir}/xorg/sarea.h \
       ${D}${includedir}/xorg/dristruct.h
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
