# Copyright (C) 2015 O.S. Systems Software LTDA.

PACKAGES += " \
    ${PN}-f2fs \
"

RDEPENDS_${PN}-f2fs = " \
    ${PN}-base \
    f2fs-tools \
"
