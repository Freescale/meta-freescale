# This recipe is modified for i.MX. For ease of maintenance the recipe itself
# is a verbatim copy of a meta-openembedded recipe, so it can be diffed against
# upstream directly; the i.MX customization lives in the required
# libcamera_0.7.1.imx.inc.
########### meta-openembedded copy ##################
# Upstream hash: f4b9dfa0c903bc94c344c657917a3fbb229c322f

SUMMARY = "Linux libcamera framework"
DESCRIPTION = "A camera stack for Linux that hides the complexity of modern camera hardware, and gives applications one interface for capture, 3A control and pipeline handling."
HOMEPAGE = "https://github.com/nxp-imx/libcamera"
SECTION = "libs"

LICENSE = "GPL-2.0-or-later AND LGPL-2.1-or-later"

LIC_FILES_CHKSUM = "\
    file://LICENSES/GPL-2.0-or-later.txt;md5=fed54355545ffd980b814dab4a3b312c \
    file://LICENSES/LGPL-2.1-or-later.txt;md5=2a4f4fd2128ea2f65047ee63fbca9f68 \
"

SRC_URI = "\
    git://git.libcamera.org/libcamera/libcamera.git;protocol=https;branch=master;tag=v${PV} \
"

# Re-set in the required libcamera_0.7.1.imx.inc for the nxp-imx fork; kept
# here to preserve the verbatim meta-openembedded copy (see header).
# UPSTREAM-PARITY.
# nooelint: oelint.var.override
SRCREV = "e2e7c015cee997b9f992376fd2c29fa2d8813e1b"

PE = "1"

# Ordering comes from the upstream recipe at the header's Upstream hash.
# nooelint: oelint.var.order.DEPENDS
DEPENDS = "chrpath-native gnutls libevent libyaml python3-jinja2-native python3-ply-native python3-pyyaml-native udev"
DEPENDS:append = " ${@bb.utils.contains('DISTRO_FEATURES', 'qt', 'qtbase qtbase-native', '', d)}"

PACKAGES =+ "${PN}-gst ${PN}-pycamera"

# Ordering comes from the upstream recipe at the header's Upstream hash.
# nooelint: oelint.var.order.PACKAGECONFIG
PACKAGECONFIG ??= ""
PACKAGECONFIG[dng] = ",,tiff"
PACKAGECONFIG[gst] = "-Dgstreamer=enabled,-Dgstreamer=disabled,gstreamer1.0 gstreamer1.0-plugins-base"
PACKAGECONFIG[pycamera] = "-Dpycamera=enabled,-Dpycamera=disabled,python3 python3-pybind11"
PACKAGECONFIG[raspberrypi] = ",,libpisp"

# Raspberry Pi requires the meta-raspberrypi layer
# These values are coming from the project's meson.build file,
# which lists the supported values by arch.
ARM_PIPELINES = "${@bb.utils.contains('PACKAGECONFIG', 'raspberrypi', 'rpi/pisp,rpi/vc4,', '', d)}"
ARM_PIPELINES .= "imx8-isi,mali-c55,simple,uvcvideo"

LIBCAMERA_PIPELINES ??= "auto"
LIBCAMERA_PIPELINES:arm ??= "${ARM_PIPELINES}"
LIBCAMERA_PIPELINES:aarch64 ??= "${ARM_PIPELINES}"

EXTRA_OEMESON = "\
    -Dpipelines=${LIBCAMERA_PIPELINES} \
    -Dv4l2=true \
    -Dcam=enabled \
    -Dlc-compliance=disabled \
    -Dtest=false \
    -Ddocumentation=disabled \
"

RDEPENDS:${PN} = "${@bb.utils.contains('DISTRO_FEATURES', 'wayland qt', 'qtwayland', '', d)}"

inherit meson pkgconfig python3native

do_configure:prepend() {
    sed -i -e 's|py_compile=True,||' ${S}/utils/codegen/ipc/mojo/public/tools/mojom/mojom/generate/template_expander.py
}

do_install:append() {
    chrpath -d ${D}${libdir}/libcamera.so
    chrpath -d ${D}${libexecdir}/libcamera/v4l2-compat.so
}

do_package:append() {
    bb.build.exec_func("do_package_recalculate_ipa_signatures", d)
}

do_package_recalculate_ipa_signatures() {
    local modules
    for module in $(find ${PKGD}/usr/lib/libcamera -name "*.so.sign"); do
        module="${module%.sign}"
        if [ -f "${module}" ] ; then
            modules="${modules} ${module}"
        fi
    done

    ${S}/src/ipa/ipa-sign-install.sh ${B}/src/ipa-priv-key.pem "${modules}"
}

# Ordering comes from the upstream recipe at the header's Upstream hash.
# nooelint: oelint.var.order.FILES
FILES:${PN} += "${libexecdir}/libcamera/v4l2-compat.so"
# -gst/-pycamera are split packages with no default FILES, so '=' is a complete
# definition. Kept byte-identical to meta-multimedia libcamera to avoid fork
# drift; suppress the append-preference warning.
# nooelint: oelint.var.filesoverride
FILES:${PN}-gst = "${libdir}/gstreamer-1.0"
# nooelint: oelint.var.filesoverride
FILES:${PN}-pycamera = "${PYTHON_SITEPACKAGES_DIR}/libcamera"

# libcamera-v4l2 explicitly sets _FILE_OFFSET_BITS=32 to get access to
# both 32 and 64 bit file APIs.
GLIBC_64BIT_TIME_FLAGS = ""
########### End of meta-openembedded copy ###########

require libcamera_0.7.1.imx.inc
