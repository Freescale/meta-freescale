FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

MESA-DEMO-PATCH = " file://Replace-glWindowPos2iARB-calls-with-glWindowPos2i.patch \
                    file://fix-clear-build-break.patch"

# only apply patches on mx6 that have a GPU
SRC_URI_append_mx6q  = " ${MESA-DEMO-PATCH}"
SRC_URI_append_mx6dl = " ${MESA-DEMO-PATCH}"
SRC_URI_append_mx6sx = " ${MESA-DEMO-PATCH}"
SRC_URI_append_mx6sl = " ${MESA-DEMO-PATCH}"

REMOVE_GLU = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', '', \
                 bb.utils.contains('DISTRO_FEATURES', 'wayland', 'glu', '', d), d)}"

# only remove GLU on mx6 thave have a GPU
PACKAGECONFIG_remove_mx6q = "${REMOVE_GLU}"
PACKAGECONFIG_remove_mx6dl = "${REMOVE_GLU}"
PACKAGECONFIG_remove_mx6sx = "${REMOVE_GLU}"
PACKAGECONFIG_remove_mx6sl = "gles1 gles2 ${REMOVE_GLU}"
