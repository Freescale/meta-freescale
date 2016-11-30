DESCRIPTION = "Userspace proxy agent for Code Warrrior HyperTrk"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://EULA;md5=c9ae442cf1f9dd6c13dfad64b0ffe73f"

SRC_URI = "git://git.freescale.com/ppc/sdk/hyperrelay.git;branch=sdk-v2.0.x"
SRCREV = "925af97359c2b86399561f1f97f2cb6ca0ccd344"

S = "${WORKDIR}/git"

CFLAGS += "\
    -Wall \
    -Wundef \
    -Wstrict-prototypes \
    -Wno-trigraphs \
    -fno-strict-aliasing \
    -fno-common \
    -O2 \
    -g \
    -fmessage-length=0 \
    -MMD \
    -MP \
    -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" \
"

LDFLAGS_prepend = " -lpthread "

do_install() {
    install -d ${D}${bindir}
    oe_runmake install DESTDIR=${D}
}

COMPATIBLE_MACHINE = "(qoriq-ppc)"
PACKAGE_ARCH = "${MACHINE_SOCARCH}"

