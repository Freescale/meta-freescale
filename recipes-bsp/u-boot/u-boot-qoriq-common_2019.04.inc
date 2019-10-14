
inherit fsl-u-boot-localversion

LICENSE = "GPLv2 & BSD-3-Clause & BSD-2-Clause & LGPL-2.0 & LGPL-2.1"
LIC_FILES_CHKSUM = " \
    file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://Licenses/bsd-2-clause.txt;md5=6a31f076f5773aabd8ff86191ad6fdd5 \
    file://Licenses/bsd-3-clause.txt;md5=4a1190eac56a9db675d58ebe86eaf50c \
    file://Licenses/lgpl-2.0.txt;md5=5f30f0716dfdd0d91eb439ebec522ec2 \
    file://Licenses/lgpl-2.1.txt;md5=4fbd65380cdd255951079008b364516c \
"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/u-boot;nobranch=1 \
"
SRCREV= "ce862bb2d2aa3b2a69bc580c57dce67d84ac1b99"

S = "${WORKDIR}/git"
PV_append = "+fslgit"
LOCALVERSION = "+fsl"
