SUMMARY = "udev rules for i.MX SoCs"
DESCRIPTION = "udev rules for Freescale i.MX SOCs"
HOMEPAGE = "https://github.com/Freescale/meta-freescale/"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://10-imx.rules;beginline=1;endline=1;md5=b2dccaa94b3629a08bfb4f983cad6f89"

SRC_URI = "file://10-imx.rules"

S = "${UNPACKDIR}"

do_install () {
    install -D -m 0644 ${UNPACKDIR}/10-imx.rules \
                       ${D}${sysconfdir}/udev/rules.d/10-imx.rules
}
