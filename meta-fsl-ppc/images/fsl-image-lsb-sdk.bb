IMAGE_FEATURES += "tools-sdk dev-pkgs ssh-server-openssh"
EXTRA_IMAGE_FEATURES = "tools-debug tools-profile tools-testapps debug-tweaks"

IMAGE_INSTALL = "\
    ${CORE_IMAGE_BASE_INSTALL} \
    packagegroup-core-basic \
    packagegroup-core-lsb \
    git \
    dtc \
    flex \
    bison \
    ccache \
    u-boot \
    "

inherit core-image

IMAGE_FSTYPES = "tar.gz"
