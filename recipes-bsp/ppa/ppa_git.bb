require ppa.inc

PPA_PATH_ls1012a = "ls1012"
PPA_PATH_ls1043a = "ls1043"
PPA_PATH_ls1046a = "ls1046"
PPA_PATH_ls2088a = "ls2088"
PPA_PATH_ls1088a = "ls1088"

do_compile () {
    export CROSS_COMPILE="${WRAP_TARGET_PREFIX}"
    cd ${S}/ppa
    if [ ${MACHINE} = ls1012afrdm ];then
        ./build  frdm-fit ${PPA_PATH}
    else
        ./build  rdb-fit ${PPA_PATH}
    fi
    cd ${S}
}

COMPATIBLE_MACHINE = "(ls1043a|ls1046a|ls2088a|ls1012a|ls1088a)"
