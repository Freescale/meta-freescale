# Copyright (C) 2013, 2014 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

require libfslparser.inc

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "8c80f20532ae178077f59d38f4e8f9b7"
SRC_URI[sha256sum] = "ae3fcdc42f59ddb6c451e42f1f81860eaa540cbfba260ecf55fc457b49f1a65d"

COMPATIBLE_MACHINE = "(mx28|mx5|mx6)"
