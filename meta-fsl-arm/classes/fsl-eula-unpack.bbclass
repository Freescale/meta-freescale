# fsl-eula-unpack.bbclass provides the mechanism used for unpacking
# the .bin file downloaded by HTTP and handle the EULA acceptance.
#
# To use it, the 'fsl-eula' parameter needs to be added to the
# SRC_URI entry, e.g:
#
#  SRC_URI = "${FSL_MIRROR}/firmware-imx-${PV};fsl-eula=true"

python fsl_bin_do_unpack() {
    src_uri = (d.getVar('SRC_URI', True) or "").split()
    if len(src_uri) == 0:
        return

    localdata = bb.data.createCopy(d)
    bb.data.update_data(localdata)

    rootdir = localdata.getVar('WORKDIR', True)
    fetcher = bb.fetch2.Fetch(src_uri, localdata)

    for url in fetcher.ud.values():
        save_cwd = os.getcwd()
        # Check for supported fetchers
        if url.type in ['http', 'https', 'ftp', 'file']:
            if url.parm.get('fsl-eula', False):
                # If download has failed, do nothing
                if not os.path.exists(url.localpath):
                    bb.debug(1, "Exiting as '%s' cannot be found" % url.basename)
                    return

                # Change to the working directory
                bb.note("Handling file '%s' as a Freescale's EULA binary." % url.basename)
                save_cwd = os.getcwd()
                os.chdir(rootdir)

                cmd = "sh %s --auto-accept --force" % (url.localpath)
                bb.fetch2.runfetchcmd(cmd, d, quiet=True)

    # Return to the previous directory
    os.chdir(save_cwd)
}

python do_unpack() {
    eula = d.getVar('ACCEPT_FSL_EULA', True)
    eula_file = d.getVar('FSL_EULA_FILE', True)
    pkg = d.getVar('PN', True)
    if eula == None:
        bb.fatal("To use '%s' you need to accept the Freescale EULA at '%s'. "
                 "Please read it and in case you accept it, write: "
                 "ACCEPT_FSL_EULA = \"1\" in your local.conf." % (pkg, eula_file))
    elif eula == '0':
        bb.fatal("To use '%s' you need to accept the Freescale EULA." % pkg)
    else:
        bb.note("Freescale EULA has been accepted for '%s'" % pkg)

    try:
        bb.build.exec_func('base_do_unpack', d)
    except:
        raise

    bb.build.exec_func('fsl_bin_do_unpack', d)
}

do_unpack[vardepsexclude] += "FSL_EULA_FILE"
