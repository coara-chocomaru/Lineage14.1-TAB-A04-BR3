#
# Copyright (C) 2022 The LineageOS Project
#
# SPDX-License-Identifier: Apache-2.0
#

import common
import re

def FullOTA_InstallEnd(info):
  OTA_InstallEnd(info)
  return

def IncrementalOTA_InstallEnd(info):
  OTA_InstallEnd(info)
  return

def AddImage(info, basename, dest, zip=True):
  name = basename
  data = info.input_zip.read("IMAGES/" + basename)
  if zip: common.ZipWriteStr(info.output_zip, name, data)
  info.script.AppendExtra('package_extract_file("%s", "%s");' % (name, dest))

def OTA_InstallEnd(info):
  info.script.Print("Patching firmware images...")
  AddImage(info, "recovery.img", "/dev/block/bootdevice/by-name/recovery")
  AddImage(info, "lk.bin", "/dev/block/bootdevice/by-name/lk")
  AddImage(info, "tz.img", "/dev/block/bootdevice/by-name/tee1")
  AddImage(info, "lk.bin", "/dev/block/bootdevice/by-name/lk2")
  AddImage(info, "tz.img", "/dev/block/bootdevice/by-name/tee2")
  return
