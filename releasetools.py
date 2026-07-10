import common

def FullOTA_InstallEnd(info):
    OTA_InstallEnd(info, info.input_zip)

def IncrementalOTA_InstallEnd(info):
    OTA_InstallEnd(info, info.target_zip)

def AddImage(info, input_zip, basename, dest):
    path = "RADIO/" + basename
    if path not in input_zip.namelist():
        print("Warning: %s not found in target_files; skipping" % path)
        return
    data = input_zip.read(path)
    common.ZipWriteStr(info.output_zip, basename, data)
    tmp = "/tmp/" + basename
    info.script.AppendExtra('package_extract_file("%s", "%s");' % (basename, tmp))
    info.script.AppendExtra('write_raw_image("%s", "%s");' % (tmp, dest))

def OTA_InstallEnd(info, input_zip):
    info.script.Print("Patching firmware images...")
    AddImage(info, input_zip, "logo.img", "/dev/block/bootdevice/by-name/logo")
    AddImage(info, input_zip, "lk.img", "/dev/block/bootdevice/by-name/lk")
    AddImage(info, input_zip, "lk.img", "/dev/block/bootdevice/by-name/lk2")
    AddImage(info, input_zip, "tee.img", "/dev/block/bootdevice/by-name/tee1")
    AddImage(info, input_zip, "tee.img", "/dev/block/bootdevice/by-name/tee2")
    AddImage(info, input_zip, "preloader.img", "/dev/block/mmcblk0boot0")
    AddImage(info, input_zip, "preloader.img", "/dev/block/mmcblk0boot1")
    AddImage(info, input_zip, "twrp.img", "/dev/block/bootdevice/by-name/recovery")
    AddImage(info, input_zip, "factory.img", "/dev/block/bootdevice/by-name/factory")
    return
