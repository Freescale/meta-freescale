DESCRIPTION = "Add packages for opencv i.MX build"

inherit packagegroup

OPENCV_PKGS = " \
    opencv-apps \
    opencv-samples \
    python3-opencv \
"
RDEPENDS:${PN} = " \
    ${OPENCV_PKGS} \
"
