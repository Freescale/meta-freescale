FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# OpenGL is not required for parts with GPU support for 2D but not 3D
IMX_REQUIRED_DISTRO_FEATURES_REMOVE          = ""
IMX_REQUIRED_DISTRO_FEATURES_REMOVE:imxgpu2d = "opengl"
IMX_REQUIRED_DISTRO_FEATURES_REMOVE:imxgpu3d = ""
REQUIRED_DISTRO_FEATURES:remove = "${IMX_REQUIRED_DISTRO_FEATURES_REMOVE}"

SRC_URI:append:mx6sl-nxp-bsp = " file://weston.config"

PACKAGECONFIG ??= " \
    no-idle-timeout \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'xwayland', '', d)} \
    ${PACKAGECONFIG_GBM_FORMAT} \
    ${PACKAGECONFIG_REPAINT_WINDOW} \
    ${PACKAGECONFIG_SIZE} \
    ${PACKAGECONFIG_USE_G2D} \
"

# Mainline BSPs dont support xwayland
PACKAGECONFIG:remove:use-mainline-bsp = "xwayland"

PACKAGECONFIG_GBM_FORMAT               ?= ""
PACKAGECONFIG_GBM_FORMAT:mx8mq-nxp-bsp ?= "gbm-format"
PACKAGECONFIG_GBM_FORMAT:mx93-nxp-bsp  ?= "gbm-format"

GBM_FORMAT_VALUE:mx8mq-nxp-bsp = "argb8888"
GBM_FORMAT_VALUE:mx93-nxp-bsp  = "argb8888"

PACKAGECONFIG_REPAINT_WINDOW             ?= ""
PACKAGECONFIG_REPAINT_WINDOW:mx8-nxp-bsp ?= "repaint-window"
PACKAGECONFIG_REPAINT_WINDOW:mx9-nxp-bsp ?= "repaint-window"

PACKAGECONFIG_SIZE                     ?= ""
PACKAGECONFIG_SIZE:mx8mq-nxp-bsp       ?= "size"

SIZE_VALUE:mx8mq-nxp-bsp = "1920x1080"

HAS_G2D          = "false"
HAS_G2D:imxgpu2d = "true"

PACKAGECONFIG_USE_G2D                ?= ""
PACKAGECONFIG_USE_G2D:imxgpu2d       ?= "use-g2d"
PACKAGECONFIG_USE_G2D:mx8qm-nxp-bsp  ?= ""
PACKAGECONFIG_USE_G2D:mx8qxp-nxp-bsp ?= ""
PACKAGECONFIG_USE_G2D:mx8dx-nxp-bsp  ?= ""
PACKAGECONFIG_USE_G2D:mx93-nxp-bsp   ?= "use-g2d"
PACKAGECONFIG_USE_G2D:mx943-nxp-bsp   ?= "use-g2d"

USE_G2D_VALUE             = "true"
USE_G2D_VALUE:mx6-nxp-bsp = "1"
USE_G2D_VALUE:mx7-nxp-bsp = "1"

PACKAGECONFIG[gbm-format] = ",,"
PACKAGECONFIG[rdp] = ",,"
PACKAGECONFIG[repaint-window] = ",,"
PACKAGECONFIG[size] = ",,"
PACKAGECONFIG[use-g2d] = ",,"
PACKAGECONFIG[xwayland] = ",,"

update_file() {
    sed -i -e "s,$1,$2," $3
}

insert_line_before() {
    sed -i -e "/$1/i $2" $3
}

insert_line_after() {
    sed -i -e "/$1/a $2" $3
}

do_install:append() {
    # Replace the template variables
    sed -i -e 's,@bindir@,${bindir},g' ${D}${sysconfdir}/xdg/weston/weston.ini

    if [ -f "${UNPACKDIR}/weston.config" ]; then
        install -Dm0755 ${UNPACKDIR}/weston.config ${D}${sysconfdir}/default/weston
    fi

    if [ "${@bb.utils.contains('PACKAGECONFIG', 'gbm-format', 'yes', 'no', d)}" = "yes" ]; then
        sed -i -e "/^\[core\]/a gbm-format=${GBM_FORMAT_VALUE}" ${D}${sysconfdir}/xdg/weston/weston.ini
    fi

    if [ "${@bb.utils.contains('PACKAGECONFIG', 'rdp', 'yes', 'no', d)}" = "yes" ]; then
        sed -i -e "s|^command=${bindir}/weston .*|& --rdp-tls-cert=${sysconfdir}/freerdp/keys/server.crt --rdp-tls-key=${sysconfdir}/freerdp/keys/server.key|" ${D}${sysconfdir}/xdg/weston/weston.ini
        sed -i -e "/^\[core\]/a modules=screen-share.so" ${D}${sysconfdir}/xdg/weston/weston.ini
    fi

    if [ "${@bb.utils.contains('PACKAGECONFIG', 'repaint-window', 'yes', 'no', d)}" = "yes" ]; then
        sed -i -e "/^\[core\]/a repaint-window=16" ${D}${sysconfdir}/xdg/weston/weston.ini
    fi

    if [ "${@bb.utils.contains('PACKAGECONFIG', 'size', 'yes', 'no', d)}" = "yes" ]; then
        sed -i -e "/^\[shell\]/a size=${SIZE_VALUE}" ${D}${sysconfdir}/xdg/weston/weston.ini
    fi

    if [ "${@bb.utils.contains('PACKAGECONFIG', 'use-g2d', 'yes', 'no', d)}" = "yes" ]; then
        sed -i -e "/^\[core\]/a use-g2d=${USE_G2D_VALUE}" ${D}${sysconfdir}/xdg/weston/weston.ini
    elif ${HAS_G2D}; then
        sed -i -e "/^\[core\]/a #use-g2d=${USE_G2D_VALUE}" ${D}${sysconfdir}/xdg/weston/weston.ini
    fi

    if [ "${@bb.utils.contains('PACKAGECONFIG', 'xwayland', 'yes', 'no', d)}" = "no" ]; then
        sed -i -e "s/^xwayland=true/#xwayland=true/g" ${D}${sysconfdir}/xdg/weston/weston.ini
    fi

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        # Add weston.log back, used by NXP for testing
        update_file "ExecStart=/usr/bin/weston " "ExecStart=/usr/bin/weston --log=\$\{XDG_RUNTIME_DIR\}/weston.log " ${D}${systemd_system_unitdir}/weston.service

        # FIXME: weston should be run as weston, not as root
        update_file "User=weston" "User=root" ${D}${systemd_system_unitdir}/weston.service
        update_file "Group=weston" "Group=root" ${D}${systemd_system_unitdir}/weston.service

        # FIXME: Upstream this change
        insert_line_before "ExecStart=" "ExecStartPre=+chvt 7" ${D}${systemd_system_unitdir}/weston.service

        # FIXME: fix the underlying problem and drop this workaround
        insert_line_after "ExecStart=" "Restart=on-failure" ${D}${systemd_system_unitdir}/weston.service
    else
        # Install weston-socket.sh for sysvinit as well
        install -D -p -m0644 ${S}/weston-socket.sh ${D}${sysconfdir}/profile.d/weston-socket.sh
    fi

    # Include commented gbm-format
    if ! [ "${@bb.utils.contains('PACKAGECONFIG', 'gbm-format', 'yes', 'no', d)}" = "yes" ]; then
        sed -i -e "/^\[core\]/a #gbm-format=${GBM_FORMAT_VALUE}" ${D}${sysconfdir}/xdg/weston/weston.ini
    fi
}

do_install:append:mx6-nxp-bsp() {
    update_file "--no-resizeable" "--no-clients-resize" ${D}${sysconfdir}/xdg/weston/weston.ini
}

do_install:append:mx7-nxp-bsp() {
    update_file "--no-resizeable" "--no-clients-resize" ${D}${sysconfdir}/xdg/weston/weston.ini
}
