# OpenEmbedded/Yocto Project BSP Layer for NXP's Platforms

Welcome to `meta-freescale`. This document outlines our commitment to providing consistent support and updates in alignment with the Yocto Project LTS release schedule.

This layer provides support for NXP's platforms for use with OpenEmbedded and/or Yocto Project.

## Maintainer

Otavio Salvador <otavio.salvador@ossystems.com.br>

## Dependencies

This layer depends on:

- URI: git://git.openembedded.org/openembedded-core
- Branch: master
- Revision: HEAD

## NXP End User License Agreement

Some components, such as the boot and DDR firmware, the GPU and VPU drivers and the codecs, are distributed by NXP under the [NXP Software License Agreement](EULA). Those recipes fail to build until the EULA is accepted:

```
ERROR: firmware-imx-1_8.32-r0 do_unpack: To use 'firmware-imx' you need to accept the Freescale EULA at '.../meta-freescale/EULA'. Please read it and in case you accept it, write: ACCEPT_FSL_EULA = "1" in your local.conf.
```

i.MX 8 and i.MX 9 machines need it even for a minimal image, since their boot image includes NXP firmware.

After reading the EULA, accept it by enabling the `eula/accept-fsl-eula` fragment:

```sh
bitbake-config-build enable-fragment freescale-layer/eula/accept-fsl-eula
```

or by adding to `conf/local.conf`:

```
ACCEPT_FSL_EULA = "1"
```

## Branches

- **master:** This is our primary development branch, receiving continuous bug fixes and BSP upgrades. It represents the latest and greatest version, ensuring compatibility with the most recent Yocto Project release.
- **wrynose:** Associated with Yocto Project 6.0 (LTS), maintained until April 2030 for bug fixes and until April 2028 for BSP backports.
- **scarthgap:** Associated with Yocto Project 5.0 (LTS), maintained until April 2028 for bug fixes. BSP backports ended in April 2026.

## Maintenance Policy

- **Latest and Greatest (Master):** Continuous attention to bug fixes and BSP upgrades, ensuring that our users have access to the latest enhancements and improvements.
- **Stable Releases:** Each stable release is maintained until the subsequent version is released. This includes backports for BSP and critical bug fixes, along with updates for security vulnerabilities (CVEs).
- **Long-Term Support (LTS) Releases:** These are maintained for the duration of Yocto Project's LTS support. We prioritize bug fixes, security vulnerabilities (CVEs) updates, and BSP backports to ensure stability and reliability.

This policy ensures that our users can rely on consistent support and updates, backed by our commitment to delivering high-quality BSP layers.

## Release Cycle

- **Yocto Project LTS Releases:** Our LTS branches follow the [Yocto Project LTS release schedule](https://wiki.yoctoproject.org/wiki/Releases), which involves a four-year maintenance period. A new LTS version is typically released every two years.
- **Stable Releases:** We aim to provide stable releases every six months, aligning with the pace of Yocto Project's stable releases.

## Contributing

Please submit any patches against the `meta-freescale` layer by using the GitHub pull-request feature. Fork the repo, create a branch, do the work, rebase from upstream, and then create the pull request.

For useful guidelines on submitting patches, please refer to the [Commit Patch Message Guidelines](http://openembedded.org/wiki/Commit_Patch_Message_Guidelines).

Pull requests will be discussed within the GitHub pull-request infrastructure. If you want to stay informed about new PRs and follow-up discussions, please use GitHub's notification system. Additionally, feel free to open GitHub issues for bug reports, feature requests, or general discussions.

## Communication

- **GitHub Issues:** [meta-freescale issues](https://github.com/Freescale/meta-freescale/issues)
- **GitHub Discussions:** [meta-freescale discussions](https://github.com/Freescale/meta-freescale/discussions)
- **Pull Requests:** [meta-freescale pull requests](https://github.com/Freescale/meta-freescale/pulls)
