include imx-test.inc

PR = "${INC_PR}.0"

COMPATIBLE_MACHINE = "(mx5)"

SRC_URI += "file://0001-ENGR00158471-fix-ipu-unit-test-application-missing-i.patch \
            file://0002-ENGR00170223-vpu-Fix-encoder-with-rotation-90-or-270.patch \
            file://0003-ENGR00162747-fix-asrc-sample-rate-convert-issue.patch"
SRC_URI[md5sum] = "5512dc0340cb71087c78f13bb6710ee0"
SRC_URI[sha256sum] = "b788ac9c787b665c49596726e0095cef9ad835be4c616d2454543ae5f41a81af"
