# -*- python -*-
# Automatically set extend the MACHINEOVERRIDES
#
# This allow to grouping of different settings for similar platforms.
#
# To use the class, specify, for example:
#
# MACHINEOVERRIDES_EXTENDER_soc = "group1:group2"
#
# Copyright 2016 (C) O.S. Systems Software LTDA.

def machine_overrides_extender(d):
    machine_overrides = (d.getVar('MACHINEOVERRIDES', True) or '').split(':')
    for o in machine_overrides:
        extender = d.getVar('MACHINEOVERRIDES_EXTENDER_%s' % o, True)
        if extender:
            extender = extender.split(':')
            extender.reverse()
            if not set(extender).issubset(set(machine_overrides)):
                index = machine_overrides.index(o)
                for e in extender:
                    machine_overrides.insert(index, e)
    d.setVar('MACHINEOVERRIDES', ':'.join(machine_overrides))

python machine_overrides_extender_handler() {
    machine_overrides_extender(e.data)
}
machine_overrides_extender_handler[eventmask] = "bb.event.ConfigParsed"
addhandler machine_overrides_extender_handler
