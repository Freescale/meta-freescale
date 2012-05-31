include imx-test.inc

PR = "${INC_PR}.0"

COMPATIBLE_MACHINE = "(mx5)"

SRC_URI += "file://0001-ENGR00158471-fix-ipu-unit-test-application-missing-i.patch \
            file://0002-ENGR00170223-vpu-Fix-encoder-with-rotation-90-or-270.patch \
            file://0003-ENGR00162747-fix-asrc-sample-rate-convert-issue.patch"
