# fsl-eula-unpack.bbclass provides the mechanism used for unpacking
# the .bin file downloaded by HTTP and handle the EULA acceptance.
#
# To use it, the 'fsl-eula' parameter needs to be added to the
# SRC_URI entry, e.g:
#
#  SRC_URI = "${FSL_MIRROR}/firmware-imx-${PV};fsl-eula=true"

FSL_EULA_FILE_MD5SUM_LA_OPT_BASE_LICENSE_V24        = "ab61cab9599935bfe9f700405ef00f28"
FSL_EULA_FILE_MD5SUM_LA_OPT_NXP_SOFTWARE_LICENSE_V9 = "6c12031a11b81db21cdfe0be88cac4b3"
FSL_EULA_FILE_MD5SUMS = \
    "${FSL_EULA_FILE_MD5SUM_LA_OPT_BASE_LICENSE_V24} \
     ${FSL_EULA_FILE_MD5SUM_LA_OPT_NXP_SOFTWARE_LICENSE_V9}"

# The checksum for the EULA in the layer
FSL_EULA_FILE_MD5SUM ?= \
    "${FSL_EULA_FILE_MD5SUM_LA_OPT_NXP_SOFTWARE_LICENSE_V9}"

LIC_FILES_CHKSUM_LAYER ?= "file://${FSL_EULA_FILE};md5=${FSL_EULA_FILE_MD5SUM}"
LIC_FILES_CHKSUM_append = " ${LIC_FILES_CHKSUM_LAYER}"

LIC_FILES_CHKSUM[vardepsexclude] += "FSL_EULA_FILE"

do_fetch_prepend() {
    if "Proprietary" not in d.getVar("LICENSE"):
        bb.fatal("The recipe LICENSE should include Proprietary but is " + d.getVar("LICENSE") + ".")
}

python fsl_bin_do_unpack() {
    src_uri = (d.getVar('SRC_URI') or "").split()
    if len(src_uri) == 0:
        return

    localdata = bb.data.createCopy(d)
    bb.data.update_data(localdata)

    rootdir = localdata.getVar('WORKDIR', True)
    fetcher = bb.fetch2.Fetch(src_uri, localdata)

    found = 0
    for url in fetcher.ud.values():
        # Skip this fetcher if it's not under EULA or if the fetcher type is not supported
        if not url.parm.get('fsl-eula', False) or url.type not in ['http', 'https', 'ftp', 'file']:
            continue
        # If download has failed, do nothing
        if not os.path.exists(url.localpath):
            bb.debug(1, "Exiting as '%s' cannot be found" % url.basename)
            return
        found += 1
        bb.note("Handling file '%s' as a Freescale EULA-licensed archive." % url.basename)
        cmd = "sh %s --auto-accept --force" % (url.localpath)
        bb.fetch2.runfetchcmd(cmd, d, quiet=True, workdir=rootdir)

    # Check for two EULAs, one from the layer and one from the package
    bb.note("Checking LIC_FILES_CHKSUM for Freescale EULA consistency...")
    if found > 1:
        bb.warn("The package contains multiple Freescale EULA-licensed archives. The consistency logic may not be able to detect a EULA problem.")
    layer_license = d.getVar('LIC_FILES_CHKSUM_LAYER')
    licenses = d.getVar('LIC_FILES_CHKSUM') or ""
    md5sums = d.getVar('FSL_EULA_FILE_MD5SUMS') or ""
    found_layer_license = False
    found_package_license = False
    for license in licenses.split():
        if license == layer_license:
            bb.note("Found Freescale EULA for the layer %s." % license)
            found_layer_license = True
            continue
        try:
            (method, host, path, user, pswd, parm) = bb.fetch.decodeurl(license)
            if method != "file" or not path:
                raise bb.fetch.MalformedUrl()
        except bb.fetch.MalformedUrl:
            bb.fatal("%s: LIC_FILES_CHKSUM contains an invalid URL:  %s" % (d.getVar('PF'), license))
        if parm.get('md5') in md5sums:
            bb.note("Found Freescale EULA for the package %s." % license)
            found_package_license = True
    if not found_layer_license:
        bb.fatal("The Freescale layer EULA '%s' is not listed in LIC_FILES_CHKSUM '%s'."
                 % (layer_license, licenses))
    if not found_package_license:
        bb.fatal("A valid package EULA with md5sum in %s was not found in LIC_FILES_CHKSUM '%s'."
                 % (md5sums.split(), licenses))
}

python do_unpack() {
    eula = d.getVar('ACCEPT_FSL_EULA')
    eula_file = d.getVar('FSL_EULA_FILE')
    pkg = d.getVar('PN')
    if eula == None:
        bb.fatal("To use '%s' you need to accept the Freescale EULA at '%s'. "
                 "Please read it and in case you accept it, write: "
                 "ACCEPT_FSL_EULA = \"1\" in your local.conf." % (pkg, eula_file))
    elif eula == '0':
        bb.fatal("To use '%s' you need to accept the Freescale EULA." % pkg)
    else:
        bb.note("Freescale EULA has been accepted for '%s'" % pkg)

    # The binary unpack needs to be done first so 'S' is valid
    bb.build.exec_func('fsl_bin_do_unpack', d)

    try:
        bb.build.exec_func('base_do_unpack', d)
    except:
        raise
}

do_unpack[vardepsexclude] += "FSL_EULA_FILE"
