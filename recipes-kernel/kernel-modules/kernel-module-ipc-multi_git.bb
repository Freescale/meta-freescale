require kernel-module-ipc.inc

EXTRA_OEMAKE ="KERNEL_DIR=${STAGING_KERNEL_DIR} B4860=1 CONFIG_MULTI_RAT=1"

do_install(){
    install -d ${D}/usr/driver/IPC/multi_rat
    install -m 755 ${S}/kernel/*.ko ${D}/usr/driver/IPC/multi_rat
}

FILES_${PN} += "/usr/driver/IPC/multi_rat/*.ko"
FILES_${PN}-dbg += "/usr/driver/IPC/multi_rat/.debug"

COMPATIBLE_MACHINE = "(b4860qds|b4420qds)"
