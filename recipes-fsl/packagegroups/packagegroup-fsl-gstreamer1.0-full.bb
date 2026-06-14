DESCRIPTION = "Package group used by FSL Community to provide all GStreamer plugins from the \
base, good, and bad packages, as well as the ugly and libav ones if commercial packages \
are whitelisted, and plugins for the required hardware acceleration (if supported by the SoC)."
SUMMARY = "FSL Community package group - full set of all GStreamer 1.0 plugins"

inherit packagegroup

RDEPENDS:${PN} = " \
    packagegroup-fsl-gstreamer1.0 \
    ${@bb.utils.contains('LICENSE_FLAGS_ACCEPTED', 'commercial', 'packagegroup-fsl-gstreamer1.0-commercial', '', d)} \
    gstreamer1.0-plugins-base-meta \
    gstreamer1.0-plugins-good-meta \
    gstreamer1.0-plugins-bad-meta \
    ${@bb.utils.contains('LICENSE_FLAGS_ACCEPTED', 'commercial', 'gstreamer1.0-plugins-ugly-meta', '', d)} \
"
