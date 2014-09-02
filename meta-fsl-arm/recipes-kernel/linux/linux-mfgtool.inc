# Produces a Manufacturing Tool compatible Linux Kernel
#
# This makes a separated binary set for Manufacturing Tool use
# without clobbering the Linux Kernel used for normal use.
#
# This file must be included after the original linux-imx.inc file
# as it overrides the needed values.
#
# Copyright (C) 2014 O.S. Systems Software LTDA.

# Adjust provides
PROVIDES = "linux-mfgtool"

# Avoid coliding files
LOCALVERSION_append = "-mfgtool"
KERNEL_SRC_PATH = "/usr/src/kernel-mfgtool"
KERNEL_IMAGE_BASE_NAME = "${KERNEL_IMAGETYPE}-mfgtool-${PKGE}-${PKGV}-${PKGR}-${MACHINE}-${DATETIME}"
KERNEL_IMAGE_SYMLINK_NAME = "${KERNEL_IMAGETYPE}-mfgtool-${MACHINE}"
MODULE_IMAGE_BASE_NAME = "modules-mfgtool-${PKGE}-${PKGV}-${PKGR}-${MACHINE}-${DATETIME}"
MODULE_TARBALL_SYMLINK_NAME = "modules-mfgtool-${MACHINE}.tgz"

# Ensures we prefer our mfgtool specific files
FILESEXTRAPATHS_prepend = "${THISDIR}/${ORIG_PN}-${PV}:${THISDIR}/${ORIG_PN}:"

python mfgtool_recipe_handler () {
    pn = e.data.getVar("PN", True)
    if not (pn.endswith("-mfgtool") or pn.startswith("mfgtool-")):
        return

    e.data.setVar("MLPREFIX", "mfgtool-")
    e.data.setVar("ORIG_PN", e.data.getVar("PN", True))
    e.data.setVar("PN", "mfgtool-" + e.data.getVar("PN", True).replace("-mfgtool", "").replace("mfgtool-", ""))
}

python () {
    pn = d.getVar("PN", True)
    if not pn.startswith("mfgtool-"):
        return

    from oe.classextend import ClassExtender

    class LinuxMfgToolExtender(ClassExtender):
        def extend_name(self, name):
            if name.startswith("rtld"):
                return name
            if name.endswith("-" + self.extname):
                name = name.replace("-" + self.extname, "")
            if name.startswith("virtual/"):
                subs = name.split("/", 1)[1]
                if not subs.startswith(self.extname):
                    return "virtual/" + self.extname + "-" + subs
                return name
            if not name.startswith(self.extname):
                return self.extname + "-" + name
            return name

    clsextend = LinuxMfgToolExtender("mfgtool", d)
    clsextend.rename_packages()
    clsextend.rename_package_variables((d.getVar("PACKAGEVARS", True) or "").split())

    clsextend.map_packagevars()
    clsextend.map_regexp_variable("PACKAGES_DYNAMIC")
}

addhandler mfgtool_recipe_handler
mfgtool_recipe_handler[eventmask] = "bb.event.RecipePreFinalise"

# FIXME: We need to remove the KERNEL_SRC_PATH contents as
#        kernel.bbclass has no way to skip its installation.
# FIXME: It is not possible to have external modules against
#        this kernel variant.
do_install_append() {
	rm -r ${D}${KERNEL_SRC_PATH}
	rmdir ${D}/usr/src ${D}/usr
}

# FIXME: We must to avoid the staging of KERNEL_SRC_PATH as
#        it is being removed (see above FIXME items).
sysroot_stage_all() {
    :
}
