import common

def FullOTA_InstallEnd(info):
    OTA_InstallEnd(info, info.input_zip)

def IncrementalOTA_InstallEnd(info):
    OTA_InstallEnd(info, info.target_zip)

def AddImage(info, input_zip, basename, dest, step):
    path = "RADIO/" + basename
    if path not in input_zip.namelist():
        info.script.Print("Warning: %s not found in target_files; skipping" % path)
        info.script.AppendExtra('set_progress(%.6f);' % step)
        return

    info.script.Print("Flashing %s" % basename)
    data = input_zip.read(path)
    common.ZipWriteStr(info.output_zip, basename, data)

    tmp = "/tmp/" + basename
    info.script.AppendExtra('package_extract_file("%s", "%s");' % (basename, tmp))
    info.script.AppendExtra('write_raw_image("%s", "%s");' % (tmp, dest))
    info.script.AppendExtra('set_progress(%.6f);' % step)

def OTA_InstallEnd(info, input_zip):
    images = [
        ("logo.img", "/dev/block/platform/bootdevice/by-name/logo"),
        ("lk.img", "/dev/block/platform/bootdevice/by-name/lk"),
        ("lk.img", "/dev/block/platform/bootdevice/by-name/lk2"),
        ("tee.img", "/dev/block/platform/bootdevice/by-name/tee1"),
        ("tee.img", "/dev/block/platform/bootdevice/by-name/tee2"),
        ("preloader.img", "/dev/block/mmcblk0boot0"),
        ("preloader.img", "/dev/block/mmcblk0boot1"),
        ("twrp.img", "/dev/block/platform/bootdevice/by-name/recovery"),
        ("factory.img", "/dev/block/platform/bootdevice/by-name/factory"),
    ]

    total = float(len(images))
    step = 0.90 / total

    info.script.Print("Patching firmware images...")
    info.script.AppendExtra('show_progress(0.90, 30);')

    for basename, dest in images:
        AddImage(info, input_zip, basename, dest, step)

    return
