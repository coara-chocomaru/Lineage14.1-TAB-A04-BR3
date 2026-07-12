LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE := custom_package_filter
LOCAL_MODULE_TAGS := optional
LOCAL_MODULE_CLASS := ETC
LOCAL_SRC_FILES := /dev/null 
LOCAL_OVERRIDES_PACKAGES := \
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
    Messaging \
    Exchange2 \
    BlockedNumberProvider \
    Dialer \
    SimToolkit \
    Stk

include $(BUILD_PREBUILT) 
