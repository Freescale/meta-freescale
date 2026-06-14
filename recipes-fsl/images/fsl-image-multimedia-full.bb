# This image extends fsl-image-multimedia with additional
# gstreamer plugins

require fsl-image-multimedia.bb

CORE_IMAGE_EXTRA_INSTALL += " \
    packagegroup-fsl-gstreamer1.0-full \
"
