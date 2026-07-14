SUMMARY = "ARM Trusted Firmware for QorIQ"
DESCRIPTION = "ARM Trusted Firmware (TF-A) for NXP QorIQ Layerscape SoCs."
HOMEPAGE = "https://github.com/nxp-qoriq/atf"

SECTION = "bsp"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://license.rst;md5=1dd070c98a281d18d9eefd938729b031"

ATF_BRANCH ?= "lf_v2.12"
ATF_SRC ?= "git://github.com/nxp-qoriq/atf.git;protocol=https"
SRC_URI = "${ATF_SRC};branch=${ATF_BRANCH}"
SRCREV = "a266ff458c2526a6474036a5c6648be6fdc54fe3"
