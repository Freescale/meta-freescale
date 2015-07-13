require ipc-modules.inc

EXTRA_OEMAKE ="KERNEL_DIR=${STAGING_KERNEL_DIR} ${SOC}=1"

do_install(){
    install -d ${D}/usr/driver/IPC/single_rat
    install -m 755 ${S}/kernel/*.ko ${D}/usr/driver/IPC/single_rat
}

FILES_${PN} += "/usr/driver/IPC/single_rat/*.ko"
FILES_${PN}-dbg += "/usr/driver/IPC/single_rat/.debug"
