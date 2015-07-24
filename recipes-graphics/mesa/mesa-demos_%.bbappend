FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

MESA-DEMO-PATCH = " file://Replace-glWindowPos2iARB-calls-with-glWindowPos2i.patch \
                    file://fix-clear-build-break.patch"

# only apply patches on mx6 that have a GPU
SRC_URI_append_mx6q  = " ${MESA-DEMO-PATCH}"
SRC_URI_append_mx6dl = " ${MESA-DEMO-PATCH}"
SRC_URI_append_mx6sx = " ${MESA-DEMO-PATCH}"
SRC_URI_append_mx6sl = " ${MESA-DEMO-PATCH}"

PACKAGECONFIG_remove_mx6sl = "gles1 gles2"
