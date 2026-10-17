# Enable Etnaviv and Freedreno support
PACKAGECONFIG:append:use-mainline-bsp = " gallium etnaviv freedreno panfrost"
RRECOMMENDS:${PN}-megadriver:append:use-mainline-bsp = " libdrm-etnaviv mesa-etnaviv-env"
