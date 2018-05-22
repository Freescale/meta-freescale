# -*- python -*-
# Automatically set extend the MACHINEOVERRIDES
#
# This allow to grouping of different settings for similar platforms.
#
# To indicate that a SoC contains following set of overrides, you can use:
#
# MACHINEOVERRIDES_EXTENDER_soc = "group1:group2"
#
# However to indicate that an override replaces a set of other
# overrides, you can use:
#
# MACHINEOVERRIDES_EXTENDER_FILTER_OUT_override = "group1 group2"
#
# Copyright 2016-2017 (C) O.S. Systems Software LTDA.

def machine_overrides_extender(d):
    machine_overrides = (d.getVar('MACHINEOVERRIDES', True) or '').split(':')

    # Gather the list of overrides to filter out
    machine_overrides_filter_out = []
    for override in machine_overrides:
        machine_overrides_filter_out += (d.getVar('MACHINEOVERRIDES_EXTENDER_FILTER_OUT_%s' % override, True) or '').split()

    # Drop any overrides of filter_out prior extending
    machine_overrides = [o for o in machine_overrides if o not in machine_overrides_filter_out]

    for override in machine_overrides:
        extender = d.getVar('MACHINEOVERRIDES_EXTENDER_%s' % override, True)

        if extender:
            extender = extender.split(':')

            # Drop any extension if in filter_out
            extender = [e for e in extender if e not in machine_overrides_filter_out]

            extender.reverse()
            if not set(extender).issubset(set(machine_overrides)):
                index = machine_overrides.index(override)
                for e in extender:
                    machine_overrides.insert(index, e)
    d.setVar('MACHINEOVERRIDES', ':'.join(machine_overrides))

python machine_overrides_extender_handler() {
    machine_overrides_extender(e.data)
}
machine_overrides_extender_handler[eventmask] = "bb.event.ConfigParsed"
addhandler machine_overrides_extender_handler
