DESCRIPTION = "Package group used by FSL Community to provide audio and video plugins \
that are subject to restricted licensing and/or royalties and thus require \
the 'commercial' license whitelist flag"
SUMMARY = "FSL Community package group - set of GStreamer 1.0 plugins with commercial licence flag"
LICENSE_FLAGS = "commercial"

inherit packagegroup

RDEPENDS:${PN} = " \
    packagegroup-fsl-gstreamer1.0 \
"

# Plugins from the -ugly collection which require the "commercial" flag in LICENSE_FLAGS_ACCEPTED to be set
RDEPENDS:${PN} = " \
    gstreamer1.0-plugins-ugly-asf \
    gstreamer1.0-libav \
"
