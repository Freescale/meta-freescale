# Copyright (C) 2015 O.S. Systems Software LTDA.

PACKAGES += " \
    ${PN}-f2fs \
"

RDEPENDS:${PN}-f2fs = " \
    ${PN}-base \
    f2fs-tools \
"
