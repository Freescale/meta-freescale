# FIXME: http://gcc.gnu.org/bugzilla/show_bug.cgi?id=57748
TUNE_CCARGS_mx5 := "${@oe_filter_out('-mfpu=neon', '${TUNE_CCARGS}', d)}"

