# -*- python -*-
# Automatically set extend the MACHINEOVERRIDES
#
# This allow to grouping of different settings for similar platforms.
#
# To indicate that a SoC contains following set of overrides, you can use:
#
# MACHINEOVERRIDES_EXTENDER:soc = "group1:group2"
#
# However to indicate that an override replaces a set of other
# overrides, you can use:
#
# MACHINEOVERRIDES_EXTENDER_FILTER_OUT:override = "group1 group2"
#
# Copyright 2016-2018, 2022 (C) O.S. Systems Software LTDA.

def machine_overrides_extender(d):
    machine_overrides = (d.getVar('PRISTINE_MACHINEOVERRIDES') or '').split(':')

    # Gather the list of overrides to filter out
    machine_overrides_filter_out = (d.getVar('MACHINEOVERRIDES_EXTENDER_FILTER_OUT') or '').split()
    for override in machine_overrides:
        machine_overrides_filter_out += (d.getVar('MACHINEOVERRIDES_EXTENDER_FILTER_OUT:%s' % override) or '').split()

    # Extend the overrides
    for override in machine_overrides:
        extender = d.getVar('MACHINEOVERRIDES_EXTENDER:%s' % override)

        if extender:
            extender = extender.split(':')

            # Drop any extension if in filter_out
            extender = [e for e in extender if e not in machine_overrides_filter_out]

            extender.reverse()
            if not set(extender).issubset(set(machine_overrides)):
                index = machine_overrides.index(override)
                for e in extender:
                    machine_overrides.insert(index, e)

    # Drop any overrides of filter_out after extending
    machine_overrides = [o for o in machine_overrides if o not in machine_overrides_filter_out]

    return ':'.join(machine_overrides)

python machine_overrides_extender_handler() {
    # Ideally we'd use a separate variable name for this however
    # historically NXP BSPs used this. We save it to a known good name
    # so we can reprocess OVERRIDES if/as/when needed.
    d.renameVar("MACHINEOVERRIDES", "PRISTINE_MACHINEOVERRIDES")

    # Now we add our own function intercept in instead
    d.setVar("MACHINEOVERRIDES", "${@machine_overrides_extender(d)}")
}

machine_overrides_extender_handler[eventmask] = "bb.event.ConfigParsed"
addhandler machine_overrides_extender_handler

python machineoverrides_filtered_out_qa_handler() {
    filtered_out = (d.getVar('MACHINEOVERRIDES_EXTENDER_FILTER_OUT') or "").split()
    qa_error = d.getVar('MACHINEOVERRIDES_FILTERED_OUT_QA_ERROR')

    for var in d.overridedata:
        # We need to allow the overrides being used in the extender
        # so avoid processing it.
        if 'MACHINEOVERRIDES_EXTENDER' in var:
            continue

        for (r, o) in d.overridedata[var]:
            common = list(set(o.split(":")).intersection(filtered_out))
            if len(common) > 0:
                raise bb.parse.SkipRecipe(qa_error % common)
}

machineoverrides_filtered_out_qa_handler[eventmask] = "bb.event.RecipeParsed"
addhandler machineoverrides_filtered_out_qa_handler
