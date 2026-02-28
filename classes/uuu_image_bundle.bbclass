# Class to create a zip file for an image suitable for flashing to NXP
# processors via the uuu tool.
#
# Usage:
#   For a basetype of an image, e.g. ext4.gz add
#     `IMAGE_FSTYPES += "ext4.gz.uuu-zip"`
#   to the image recipe and provide a
#     `uuu_ext4.gz.auto.in`
#   flashing script via the usual bitbake FILESPATH of the image recipe.
#
#   The base image (ext4.gz in this example) will be added to the uuu bundle.
#   Reference the image as
#     @@file_image@@
#   in your uuu script.
#
#   Refer to the documentation for uuu_bundle.bbclass for the variables
#     - `UUU_FILES`
#     - `UUU_TEXTS`
#   but suffix them with the basetype, e.g.
#     - `UUU_FILES_ext4.gz` instead of `UUU_FILES` 
#     - `UUU_TEXTS_ext4.gz` instead of `UUU_TEXTS`
#
#   The variable flags with basetype suffix, e.g.
#     - `UUU_FILE_ext4.gz[identifier]`
#     - `UUU_TEXT_ext4.gz[identifier]`
#   take presedence over the non-suffixed counterparts described in the
#   documentation for uuu_bundle.bbclass but default to the values of those
#   variables if not specified.
#
#   The variable
#     - `UUU_DEPS`
#   as described in the documentation for uuu_bundle.bbclass will be extended
#   with the values of the variable suffixed with the basetype, e.g.
#     - `UUU_DEPS_ext4.gz`
#
#   Note that `.uuu-zip` needs to be specified in IMAGE_FSTYPES, but the created
#   file will have a `.uuu.zip` extension.
#
# Copyright 2022-2025 (C) emlix GmbH, Daniel Wagenknecht <dwagenknecht@emlix.com>
#
# SPDX-License-Identifier: MIT

inherit image_types

inherit uuu_bundle
deltask do_create_uuuzip
deltask do_deploy

# We use CONVERSIONTYPES so any existing rootfs type can be converted to a
# uuu.zip without having to deploy the possibly uncompressed intermediary
# artifact. the CONVERSION_CMD just stages the file to the uuu working
# directory, the creation of the uuu.zip file happens in the do_image_uuuzip
# task.
CONVERSIONTYPES += "uuu-zip"
CONVERSION_CMD:uuu-zip = "cp ${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.${type} ${@get_uuu_workdir(d, '${type}')}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.${type} "

python do_image_uuuzip () {
    basetypes = d.getVar('UUU_IMAGE_BASETYPES').strip().split()

    for type in basetypes:
      output_file = os.path.join(d.getVar('IMGDEPLOYDIR'),d.getVar(f'UUU_IMAGE_{type}'))
      create_uuuzip(d,
        var_suffix=type,
        output_file = output_file,
        file_searchpaths="${IMGDEPLOYDIR}:${DEPLOY_DIR_IMAGE}"
      )
}
do_image_uuuzip[depends] += 'zip-native:do_populate_sysroot ${UUU_DEPS}'
do_image_uuuzip[postfuncs] += 'create_symlinks'
addtask do_image_uuuzip before do_image_complete after do_unpack do_image


do_image_prepare_uuuzip () {
    # This task exists so the uuu working directory is clean before the individual image tasks run.
    :
}
do_image_prepare_uuuzip[nostamp] = "1"
addtask do_image_prepare_uuuzip

python () {
    pn = d.getVar("PN")
    image_fstypes = d.getVar('IMAGE_FSTYPES') or ""
    if 'uuu-zip' not in image_fstypes:
      bb.warnonce(d.getVar('FILE') + " inherits uuu-image-bundle class but does not specify any uuu-zip image in IMAGE_FSTYPES")

    # normally images don't provide any inputs via SRC_URI. We need a file per
    # image type that should be converted to a uuu bundle, so we need to reactivate
    # the relevant tasks. Assume file:// protocol only, so no do_fetch is needed.
    d.delVarFlag('do_unpack', 'noexec')

    for typestring in image_fstypes.split():
      types = typestring.split(".")
      if 'uuu-zip' not in types:
        continue

      imagetask = f'do_image_{types[0]}'

      index = types.index('uuu-zip')
      basetype = ".".join(types[:index])

      image_name = d.expand('${IMAGE_NAME}${IMAGE_NAME_SUFFIX}')

      d.setVarFlag(f'UUU_FILE_{basetype}', 'image', f'{image_name}.{basetype}')
      d.appendVar(f'UUU_FILES_{basetype}', ' image')

      d.appendVar('UUU_IMAGE_BASETYPES', f' {basetype}')
      d.setVar(f'UUU_IMAGE_{basetype}', f'{image_name}.{basetype}.uuu.zip')
      d.appendVar('SRC_URI', f' file://uuu_{basetype}.auto.in')

      d.appendVarFlag(imagetask, 'depends', f' {pn}:do_image_prepare_uuuzip')
      d.appendVarFlag('do_image_prepare_uuuzip', 'cleandirs', f' {get_uuu_workdir(d, basetype)}')

      d.appendVarFlag('do_image_uuuzip', 'depends', f' {pn}:{imagetask}')
      d.appendVarFlag('do_image_uuuzip', 'subimages', f' {basetype}.uuu.zip')
}
