# Header-only library, so the main package is empty. imx-gpu-sdk RDEPENDS on it
# to pull the headers into the SDK, and only builds on imxgpu machines.
ALLOW_EMPTY:${PN}:imxgpu = "1"
