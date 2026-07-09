#
# Copyright (C) 2022 The Android Open Source Project
# Copyright (C) 2022 SebaUbuntu's TWRP device tree generator
#
# SPDX-License-Identifier: Apache-2.0
#

#$(call inherit-product, $(SRC_TARGET_DIR)/product/generic_no_telephony.mk)

$(call inherit-product, $(SRC_TARGET_DIR)/product/full_base.mk)

$(call inherit-product, $(SRC_TARGET_DIR)/product/languages_full.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/core_64_bit.mk)
$(call inherit-product, device/sts/a04br3/device.mk)

# Device identifier. This must come after all inclusions
PRODUCT_DEVICE := a04br3
PRODUCT_NAME := lineage_a04br3
PRODUCT_RELEASE_NAME := PixelTouch_3
PRODUCT_BRAND := google
PRODUCT_MODEL := PixelTouch_3
PRODUCT_MANUFACTURER := google
PRODUCT_GMS_CLIENTID_BASE := android-sts-google
PRODUCT_LOCALES := ja_JP en_US en_AU
PRODUCT_DEFAULT_LANGUAGE := ja
PRODUCT_DEFAULT_REGION := JP
PRODUCT_AAPT_CONFIG := large mdpi hdpi
PRODUCT_AAPT_PREF_CONFIG := mdpi
TARGET_SCREEN_WIDTH := 1280
TARGET_SCREEN_HEIGHT := 800
PRODUCT_RESTRICT_VENDOR_FILES := false
TEMPORARY_DISABLE_PATH_RESTRICTIONS := true

PRODUCT_BUILD_PROP_OVERRIDES += \
    PRIVATE_BUILD_DESC="sailfish-user 7.1.2 NJH47F 4146041 release-keys"

BUILD_FINGERPRINT := google/sailfish/sailfish:7.1.2/NJH47F/4146041:user/release-keys
