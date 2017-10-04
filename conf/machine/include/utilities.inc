### Machine definition file utilities

def make_dtb_boot_files(d):
    # Generate IMAGE_BOOT_FILES entries for device tree files listed in
    # KERNEL_DEVICETREE.
    alldtbs = d.getVar('KERNEL_DEVICETREE')
    imgtyp = d.getVar('KERNEL_IMAGETYPE')

    def transform(dtb):
        if dtb.endswith('dtb'):
            # eg: whatever/bcm2708-rpi-b.dtb has:
            #     DEPLOYDIR file: ${KERNEL_IMAGETYPE}-bcm2708-rpi-b.dtb
            #     destination: bcm2708-rpi-b.dtb
            base = os.path.basename(dtb)
            src = '{}-{}'.format(imgtyp, base)
            dst = base
            return '{};{}'.format(src, dst)
        elif dtb.endswith('dtbo'):
            # overlay dtb:
            # eg: overlays/hifiberry-amp.dtbo has:
            #     DEPLOYDIR file: ${KERNEL_IMAGETYPE}-hifiberry-amp.dtbo
            #     destination: overlays/hifiberry-amp.dtbo
            base = os.path.basename(dtb)
            src = '{}-{}'.format(imgtyp, base)
            dst = dtb
            return '{};{}'.format(src, dtb)

    return ' '.join([transform(dtb) for dtb in alldtbs.split(' ') if dtb])
