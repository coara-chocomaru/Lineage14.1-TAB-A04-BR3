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
PRODUCT_RELEASE_NAME := TAB-A04-BR3
PRODUCT_BRAND := benesse
PRODUCT_MODEL := TAB-A04-BR3
PRODUCT_MANUFACTURER := STS-TOTTORI
PRODUCT_GMS_CLIENTID_BASE := android-sts-tottori
PRODUCT_LOCALES := ja_JP en_US en_AU
PRODUCT_DEFAULT_LANGUAGE := ja
PRODUCT_DEFAULT_REGION   := JP
PRODUCT_AAPT_CONFIG := large mdpi hdpi
PRODUCT_AAPT_PREF_CONFIG := mdpi
TARGET_SCREEN_WIDTH := 1280
TARGET_SCREEN_HEIGHT := 800
PRODUCT_RESTRICT_VENDOR_FILES := false
TEMPORARY_DISABLE_PATH_RESTRICTIONS=true

PRODUCT_BUILD_PROP_OVERRIDES += \
    PRIVATE_BUILD_DESC="TAB-A04-BR3-userdebug 7.1.2 01.06.000 01.06.000 test-keys"

BUILD_FINGERPRINT := benesse/TAB-A04-BR3/TAB-A04-BR3:7.1.2/01.06.000/01.06.000:userdebug/test-keys
