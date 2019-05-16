### Machine definition file utilities

def make_dtb_boot_files(d):
    # Generate IMAGE_BOOT_FILES entries for device tree files listed in
    # KERNEL_DEVICETREE.
    # Use only the basename for dtb files:
    alldtbs = d.getVar('KERNEL_DEVICETREE', True)

    def transform(dtb, alldtbs):
        if dtb.endswith('dtb') or dtb.endswith('dtbo'):
            # eg: whatever/bcm2708-rpi-b.dtb has:
            #     DEPLOYDIR file: bcm2708-rpi-b.dtb
            #     destination: bcm2708-rpi-b.dtb
            return os.path.basename(dtb)
        else:
            alldtbs = alldtbs.replace(dtb, '')
            d.setVar('KERNEL_DEVICETREE', alldtbs)
            bb.warn("Ignoring KERNEL_DEVICETREE entry %s because it is not a .dtb or .dtbo file." % (dtb) )
            return ""

    return ' '.join([transform(dtb, alldtbs) for dtb in alldtbs.split() if dtb])
