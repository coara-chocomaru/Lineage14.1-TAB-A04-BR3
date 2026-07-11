# Inherit device configuration
$(call inherit-product, device/sts/a04br3/lineage_a04br3.mk)
$(call inherit-product, vendor/cm/config/common_full_tablet_wifionly.mk)
TARGET_LOCALES := ja_JP en_US en_AU
REMOVE_APPS := \
    Calendar \
    Camera2 \
    DeskClock \
    Email \
    ExactCalculator \
    Calculator \
    Jelly \
    LatinIME \
    LockClock \
    WallpaperPicker \
    AudioFX \
    Contacts \
    Eleven \
    Gallery2 \
    Recorder \
    Trebuchet \
    Exchange2 \
    BlockedNumberProvider

REMOVE_TELEPHONY := \
    Dialer \

REMOVE_NFC := \
    Nfc \
    Tag

PRODUCT_PACKAGES := $(filter-out $(REMOVE_APPS) $(REMOVE_TELEPHONY) $(REMOVE_NFC), $(PRODUCT_PACKAGES))
