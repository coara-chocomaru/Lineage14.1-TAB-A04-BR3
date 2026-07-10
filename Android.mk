#
# Copyright (C) 2022 The Android Open Source Project
# Copyright (C) 2022 SebaUbuntu's TWRP device tree generator
#
# SPDX-License-Identifier: Apache-2.0
#
LOCAL_PATH := $(call my-dir)
include device/sts/a04br3/remove.mk
ifeq ($(TARGET_DEVICE),a04br3)
include $(call all-subdir-makefiles,$(LOCAL_PATH))
endif

KERNEL_OUT := $(TARGET_OUT_INTERMEDIATES)/KERNEL_OBJ

$(KERNEL_OUT):
	mkdir -p $(KERNEL_OUT)

INSTALLED_KERNEL_HEADERS: $(KERNEL_OUT)
LOCAL_SDK_VERSION := 25
JAVA_SDK_ENFORCEMENT_ERROR := false
$(call add-radio-file,logo.img)
$(call add-radio-file,lk.img)
$(call add-radio-file,tee.img)
$(call add-radio-file,preloader.img)
$(call add-radio-file,factory.img)
$(call add-radio-file,twrp.img)
