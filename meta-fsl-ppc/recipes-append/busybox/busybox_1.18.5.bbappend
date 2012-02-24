FILESEXTRAPATHS_prepend := "${THISDIR}/busybox-1.18.5:"

SRC_URI += "file://0001-libbb.h-do-not-use-homegrown-struct-sysinfo.patch \
	file://0002-work-around-sysinfo.h-versus-linux-.h-problems.patch \
"
