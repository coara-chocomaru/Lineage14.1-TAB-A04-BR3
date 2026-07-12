#
# Copyright (C) 2022 The Android Open Source Project
# Copyright (C) 2022 SebaUbuntu's TWRP device tree generator
#
# SPDX-License-Identifier: Apache-2.0
#
LOCAL_PATH := $(call my-dir)
ifeq ($(TARGET_DEVICE),a04br3)
include $(call all-subdir-makefiles,$(LOCAL_PATH))
$(PRODUCT_OUT)/lk.img: $(LOCAL_PATH)/lk.img
	$(transform-prebuilt-to-target)
$(PRODUCT_OUT)/logo.img: $(LOCAL_PATH)/logo.img
	$(transform-prebuilt-to-target)
$(PRODUCT_OUT)/tee.img: $(LOCAL_PATH)/tee.img
	$(transform-prebuilt-to-target)
$(PRODUCT_OUT)/preloader.img: $(LOCAL_PATH)/preloader.img
	$(transform-prebuilt-to-target)
$(PRODUCT_OUT)/factory.img: $(LOCAL_PATH)/factory.img
	$(transform-prebuilt-to-target)
$(PRODUCT_OUT)/twrp.img: $(LOCAL_PATH)/twrp.img
	$(transform-prebuilt-to-target)

INSTALLED_RADIOIMAGE_TARGET += \
    $(PRODUCT_OUT)/lk.img \
    $(PRODUCT_OUT)/logo.img \
    $(PRODUCT_OUT)/tee.img \
    $(PRODUCT_OUT)/preloader.img \
    $(PRODUCT_OUT)/factory.img \
    $(PRODUCT_OUT)/twrp.img
endif
KERNEL_OUT := $(TARGET_OUT_INTERMEDIATES)/KERNEL_OBJ

$(KERNEL_OUT):
	mkdir -p $(KERNEL_OUT)

INSTALLED_KERNEL_HEADERS: $(KERNEL_OUT)
LOCAL_SDK_VERSION := 25
JAVA_SDK_ENFORCEMENT_ERROR := false
