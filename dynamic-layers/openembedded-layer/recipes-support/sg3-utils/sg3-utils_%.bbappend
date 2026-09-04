# utp-com, this layer's i.MX flashing tool, is BBCLASSEXTEND'd and DEPENDS on
# sg3-utils, so the variants must exist on the machines we provide.
BBCLASSEXTEND:imx-generic-bsp = "native nativesdk"
BBCLASSEXTEND:qoriq = "native nativesdk"
