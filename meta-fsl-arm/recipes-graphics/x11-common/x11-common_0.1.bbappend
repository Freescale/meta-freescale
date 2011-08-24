# Append path to include custom Xserver arguments
THISDIR := "${@os.path.dirname(bb.data.getVar('FILE', d, True))}"
FILESPATH =. "${@base_set_filespath(["${THISDIR}/${PN}"], d)}:"

SRC_URI_append += "file://Xserver-imx53-platform-support.patch"
