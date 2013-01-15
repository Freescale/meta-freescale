PRINC := "${@int(PRINC) + 1}"

RDEPENDS_${PN} += " \
    nativesdk-elftosb \
    nativesdk-mxsldr \
"
