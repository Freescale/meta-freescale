include imx-test.inc

DEPENDS = "virtual/kernel"

PR = "${INC_PR}.0"
PE = "1"

SRC_URI[md5sum] = "e30d557aea2ef3cc5840a3cfc81364bc"
SRC_URI[sha256sum] = "ce7dc16bc2e7e56d9394d8d899a4cdd73e416f8cec4d4a8acec946c8922028bf"

COMPATIBLE_MACHINE = "(mx28)"
