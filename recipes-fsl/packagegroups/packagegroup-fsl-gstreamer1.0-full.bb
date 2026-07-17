SUMMARY = "FSL Community package group - full set of all GStreamer 1.0 plugins"
DESCRIPTION = "Package group used by FSL Community to provide all GStreamer plugins from the \
               base, good, and bad packages, as well as the ugly and libav ones if commercial packages \
               are whitelisted, and plugins for the required hardware acceleration (if supported by the SoC)."
SECTION = "multimedia"

inherit packagegroup

# Entries are ordered alphabetically (conditional ${@...} first); the linter's
# tokenizer mis-sorts the two conditional entries, so the check is suppressed.
# nooelint: oelint.vars.dependsordered
RDEPENDS:${PN} = "\
    ${@bb.utils.contains('LICENSE_FLAGS_ACCEPTED', 'commercial', 'packagegroup-fsl-gstreamer1.0-commercial', '', d)} \
    ${@bb.utils.contains('LICENSE_FLAGS_ACCEPTED', 'commercial', 'gstreamer1.0-plugins-ugly-meta', '', d)} \
    gstreamer1.0-plugins-bad-meta \
    gstreamer1.0-plugins-base-meta \
    gstreamer1.0-plugins-good-meta \
    packagegroup-fsl-gstreamer1.0 \
"
