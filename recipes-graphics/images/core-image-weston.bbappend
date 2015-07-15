# FIXME: Vivante GPU driver cannot operate in X11 and Wayland in same
# distribution as it needs to have different libraries installed. So
# in case 'x11' is in DISTRO_FEATURES, Wayland is disabled.
CONFLICT_DISTRO_FEATURES_append_mx6 = " x11"
