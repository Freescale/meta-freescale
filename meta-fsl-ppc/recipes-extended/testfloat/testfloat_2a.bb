DESCRIPTION = "A program for testing floating-point implementation"
LICENSE = "TestFloat"

LIC_FILES_CHKSUM = "file://testfloat/testfloat.txt;beginline=87;endline=95;md5=bdb2e8111838a48015c29bd97f5b6145"

SRC_URI = " http://www.jhauser.us/arithmetic/TestFloat-2a.tar.Z;name=TestFloat \
            http://www.jhauser.us/arithmetic/SoftFloat-2b.tar.Z;name=SoftFloat \
          "
SRC_URI_append_qoriq-ppc = " file://SoftFloat-powerpc-1.patch \
                              file://TestFloat-powerpc-E500v2-SPE-1.patch \
                              file://Yocto-replace-COMPILE_PREFIX-gcc.patch \
                            "
SRC_URI[TestFloat.md5sum] = "4dc889319ae1e0c5381ec511f784553a"
SRC_URI[TestFloat.sha256sum] = "84d14aa42adefbda2ec9708b42946f7fa59f93689b042684bd027863481f8e4e"
SRC_URI[SoftFloat.md5sum] = "b4a58b5c941f1a2317e4c2500086e3fa"
SRC_URI[SoftFloat.sha256sum] = "89d14b55113a2ba8cbda7011443ba1d298d381c89d939515d56c5f18f2febf81"

S = "${WORKDIR}/TestFloat-2a"

do_unpack2(){
    mv ${WORKDIR}/SoftFloat-2b ${S}/SoftFloat-2b
    cd ${S}
    if [ -n "$(which fromdos)" ];then
        find -type f -exec fromdos {} \;
    elif [ -n "$(which dos2unix)" ];then
        find -type f -exec dos2unix {} \;
    else
        echo -e "\nERROR: command dos2unix or fromdos not found\n" && return 1
    fi
}
addtask do_unpack2 after do_unpack before do_patch

do_compile(){
    oe_runmake -C testfloat/powerpc-linux-gcc/ CC="${CC}" EXTRA_CFLAGS="-DTEST_KERNEL_EMU"
}

do_install(){
    install -d ${D}/${bindir}
    install testfloat/powerpc-linux-gcc/testfloat ${D}/${bindir}
    install testfloat/powerpc-linux-gcc/testsoftfloat ${D}/${bindir}
}

COMPATIBLE_HOST_e500v2 = ".*"
COMPATIBLE_HOST ?= "(none)"

