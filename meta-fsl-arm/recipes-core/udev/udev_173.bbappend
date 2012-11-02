PRINC := "${@int(PRINC) + 2}"

# Switch to git source
# 173 tag
SRCREV = "ad667dff51711fed763a23283d973486de3cd6b5"
SRC_URI := "${@oe_filter_out('${KERNELORG_MIRROR}/linux/utils/kernel/hotplug/udev-${PV}.tar.gz', '${SRC_URI}', d)}"
SRC_URI_prepend = "git://git.kernel.org/pub/scm/linux/hotplug/udev.git;protocol=git "
S = "${WORKDIR}/git"
