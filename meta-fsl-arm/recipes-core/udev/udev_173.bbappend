# Freescale imx extra configuration udev rules
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC := "${@int(PRINC) + 1}"

# 173 tag
SRCREV = "ad667dff51711fed763a23283d973486de3cd6b5"

# version specific SRC_URI
SRC_URI = "git://git.kernel.org/pub/scm/linux/hotplug/udev.git;protocol=git \
           file://0001-rip-put-doc-generation-it-depends-on-a-working-docto.patch \
           file://gtk-doc.make"

# generic SRC_URI
SRC_URI += " \
       file://touchscreen.rules \
       file://modprobe.rules \
       file://default \
       file://init \
       file://cache \
"

S = "${WORKDIR}/git"
