PR .= "+${DISTRO}.0"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://oprofile-0.9.6-e500mc-support.patch \
            file://oprofile-0.9.6-e500mc-support-2.patch \
            file://oprofile-0.9.6-add-e500mc-support-in-op_events.patch \
"
