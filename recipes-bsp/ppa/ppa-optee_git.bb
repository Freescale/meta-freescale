require ppa.inc

DEPENDS += "optee-os-qoriq"

PPA_PATH_ls1046a = "ls1046"
PPA_PATH_ls1043a = "ls1043"
PPA_PATH_ls1012a = "ls1012"

do_compile() {
    export CROSS_COMPILE="${WRAP_TARGET_PREFIX}"
    cp ${RECIPE_SYSROOT}/lib/firmware/tee_${MACHINE}.bin ${S}/ppa/soc-${PPA_PATH}/tee.bin
    cd ${S}/ppa
    ./build rdb-fit spd=on ${PPA_PATH}
    cd ${S}
}

COMPATIBLE_MACHINE = "(ls1043a|ls1046a|ls1012a)"
