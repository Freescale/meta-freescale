# Class to create a zip file suitable for flashing to NXP processors via the
# uuu tool.
#
# Usage:
#   Provide a `uuu.auto.in` flashing script via SRC_URI.
#   In the recipe set the Variable `UUU_FILES` to a list of identifiers
#     UUU_FILES = "bootloader whatever"
#   and matching variable flags containing the file names or paths like
#     UUU_FILE[bootloader] = "u-boot-${MACHINE}.${UBOOT_SUFFIX}-uuu"
#     UUU_FILE[whatever] = "some-file.bin"
#   Reference these files via `@@file_var@@`, e.g.
#     @@file_bootloader@@
#     @@file_whatever@@
#   placeholders in your uuu script.
#   The corresponding files will be searched in `${WORKDIR}/uuu` and
#   DEPLOY_DIR_IMAGE (in that order) and added to the uuu bundle.
#
#   Specify needed dependencies via the UUU_DEPS variable, e.g.
#     UUU_DEPS = "virtual/kernel:do_deploy"
#
#   In the recipe set the Variable `UUU_TEXTS` to a list of identifiers
#     UUU_TEXTS = "bootargs whatever"
#   and matching variable flags containing strings like
#     UUU_TEXT[bootargs] = "init=/bin/sh"
#     UUU_TEXT[whatever] = "some-string"
#   Reference these strings via `@@text_var@@`, e.g.
#     @@text_bootargs@@
#     @@text_whatever@@
#   placeholders in your uuu script.
#
# Copyright 2022-2025 (C) emlix GmbH, Daniel Wagenknecht <dwagenknecht@emlix.com>
#
# SPDX-License-Identifier: MIT

inherit deploy image-artifact-names

def get_uuu_workdir(d, var_suffix=None):
    # the parameter can be specified with or without the _ prefix
    var_suffix = "_" + var_suffix.lstrip("_") if var_suffix else ""
    workdir = d.getVar('WORKDIR')
    uuu_dir = os.path.join(workdir, 'uuu' + var_suffix)
    return uuu_dir

def create_uuuzip (d, \
                   var_suffix=None, \
                   output_file="${B}/${IMAGE_NAME}.uuu.zip", \
                   file_searchpaths=None \
                  ):
    import shutil
    from pathlib import Path

    # the parameter can be specified with or without the _ prefix
    var_suffix = "_" + var_suffix.lstrip("_") if var_suffix else ""

    # the parameter can be specified with or without the .zip ending
    output_file = d.expand(output_file.removesuffix(".zip"))

    uuu_dir = get_uuu_workdir(d, var_suffix)

    if not file_searchpaths:
      file_searchpaths = "${DEPLOY_DIR_IMAGE}"

    file_searchpaths = f'{uuu_dir}:{d.expand(file_searchpaths)}'

    unpackdir = d.getVar('UNPACKDIR')
    uuu_auto_file = f'{unpackdir}/uuu{var_suffix}.auto.in'
    if not os.path.exists(uuu_auto_file):
      bb.fatal(f'Missing uuu{var_suffix}.auto.in file.')
    uuu_auto_content = Path(uuu_auto_file).read_text()

    uuu_file_names = d.getVar('UUU_FILES' + var_suffix) or ""
    for item in uuu_file_names.split():
      if not item:
        continue
      filename = d.getVarFlag(f'UUU_FILE{var_suffix}', item) or d.getVarFlag('UUU_FILE', item)
      file = bb.utils.which(file_searchpaths, filename)
      if not file:
        bb.fatal(f'File {filename} not found in {file_searchpaths}')

      # Zip file should contain a flat directory structure containing
      # actual files, no symlinks.
      filename = os.path.basename(file)
      file = os.path.realpath(file)
      try:
        shutil.copy(file, os.path.join(uuu_dir, filename))
      except shutil.SameFileError:
        pass

      uuu_auto_content = uuu_auto_content.replace(f'@@file_{item}@@', filename)

    uuu_text_names = d.getVar('UUU_TEXTS' + var_suffix) or ""
    for item in uuu_text_names.split():
      if not item:
        continue
      text = d.getVarFlag(f'UUU_TEXT{var_suffix}', item) or d.getVarFlag('UUU_TEXT', item)
      uuu_auto_content = uuu_auto_content.replace(f'@@text_{item}@@', text)

    Path(uuu_dir, 'uuu.auto.in').write_text(uuu_auto_content)

    shutil.make_archive(output_file, 'zip', uuu_dir)

python do_create_uuuzip () {
    create_uuuzip(d)
}
addtask do_create_uuuzip after do_unpack
do_create_uuuzip[depends] += 'zip-native:do_populate_sysroot ${UUU_DEPS}'
do_create_uuuzip[cleandirs] += "${@get_uuu_workdir(d)}"

do_deploy () {
    install -d ${DEPLOYDIR}
    install -m 0644 ${B}/${IMAGE_NAME}.uuu.zip ${DEPLOYDIR}
    if [ -n "${IMAGE_LINK_NAME}" ] && [ "${IMAGE_NAME}" != "${IMAGE_LINK_NAME}" ]; then
      ln -s ${IMAGE_NAME}.uuu.zip ${DEPLOYDIR}/${IMAGE_LINK_NAME}.uuu.zip
    fi
}
addtask do_deploy after do_create_uuuzip


