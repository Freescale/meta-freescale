# fsl-eula.bbclass defines a common naming scheme used by the
# packing and unpacking mechanisms defined in fsl-eula-pack.bbclass
# and fsl-eula-unpack2.bbclass.
#
# Note that it is not necessary to inherit this class directly. It is
# already inherited from fsl-eula-pack.bbclass and fsl-eula-unpack2.bbclass.
#
# The naming scheme takes into account the fact that a single software
# package can create many archives based on the combinations of a) target
# versus native/nativesdk components, and b) the target or native architecture.
# The naming scheme can be extended through regular bitbake means to allow
# configuration-specific archives, as can be seen in fsl-eula-graphics.bbclass.

# The variable IMX_PACKAGE_NAME gives a unique name for every possible
# archive. The variable is built from a combination of the package name ${PN},
# the version ${IMX_PACKAGE_VERSION} and the target or native architecture,
# ${TARGET_ARCH} or ${BUILD_ARCH}.
IMX_PACKAGE_NAME                 = "${BPN}-${IMX_PACKAGE_VERSION}-${TARGET_ARCH}"
IMX_PACKAGE_NAME_class-native    = "${PN}-${IMX_PACKAGE_VERSION}-${BUILD_ARCH}"
IMX_PACKAGE_NAME_class-nativesdk = "${PN}-${IMX_PACKAGE_VERSION}-${BUILD_ARCH}"

# The variable SRC_URI_NAME gives a unique SRC_URI name option for use in
# unpacking recipes derived from fsl-eula-unpack2.bbclass. With this name,
# a single unpacking recipe can handle all possible archives. The name is
# built from with a combination of target or native architecture,
# ${TARGET_ARCH} or ${BUILD_ARCH}, and a native or nativesdk designation.
SRC_URI_NAME                 = "${TARGET_ARCH}"
SRC_URI_NAME_class-native    = "${BUILD_ARCH}-native"
SRC_URI_NAME_class-nativesdk = "${BUILD_ARCH}-nativesdk"
