SUMMARY = "OpenCV applications and samples for i.MX"
DESCRIPTION = "Packages providing the OpenCV computer-vision library on i.MX, \
               including example applications, samples and the Python 3 bindings."
SECTION = "multimedia"

inherit packagegroup

OPENCV_PKGS = "\
    opencv-apps \
    opencv-samples \
    python3-opencv \
"
RDEPENDS:${PN} = "\
    ${OPENCV_PKGS} \
"
