SUMMARY = "FSL Community package group - commercial-flagged GStreamer 1.0 plugins"
DESCRIPTION = "Package group used by FSL Community to provide audio and video plugins \
               that are subject to restricted licensing and/or royalties and thus require \
               the 'commercial' license whitelist flag"
SECTION = "multimedia"
LICENSE_FLAGS = "commercial"

inherit packagegroup

# Plugins from the -ugly collection which require the "commercial" flag in LICENSE_FLAGS_ACCEPTED to be set
RDEPENDS:${PN} = "\
    gstreamer1.0-libav \
    gstreamer1.0-plugins-ugly-asf \
"
