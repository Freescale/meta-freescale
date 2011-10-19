# Copyright (C) 2011 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

PACKAGES += "${PN}-libmfwsconf ${PN}-libgplaycore \
             ${PN}-libme ${PN}-libgstbufmeta ${PN}-libmfwba \
             ${PN}-libfwvss ${PN}-gplay"

FILES_${PN} = "${libdir}/gstreamer-0.10/*.so"
FILES_${PN}-dbg = "${libdir}/gstreamer-0.10/.debug \
    ${libdir}/.debug"
FILES_${PN}-dev = "${libdir}/gstreamer-0.10/*.la \
    {libdir}/*.la"

FILES_${PN}-gplay = "${bindir}/gplay"
FILES_${PN}-libmfwsconf = "${libdir}/libmfwsconf.so.*"
FILES_${PN}-libgplaycore = "${libdir}/libgplaycore.so.*"
FILES_${PN}-libme = "${libdir}/libme.so.*"
FILES_${PN}-libgstbufmeta = "${libdir}/libgstbufmeta.so.*"
FILES_${PN}-libmfwba =  "${libdir}/libmfwba.so.*"
FILES_${PN}-libfwvss = "${libdir}/libmfwvss.so.*"
