# Enable Etnaviv and Freedreno support
PACKAGECONFIG:append:use-mainline-bsp = " gallium etnaviv freedreno"
PACKAGECONFIG:append:mx95-mainline-bsp = " panfrost libclc"
PACKAGECONFIG:append:mx93-mainline-bsp = " ethosu teflon"
RRECOMMENDS:${PN}-megadriver:append:use-mainline-bsp = " libdrm-etnaviv mesa-etnaviv-env"
