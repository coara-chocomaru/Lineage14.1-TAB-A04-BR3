LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := libshim_gui3
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := GraphicBufferShim.cpp
LOCAL_SHARED_LIBRARIES := libui libutils libcutils liblog
LOCAL_CFLAGS := -Wno-unused-parameter -Wno-non-virtual-dtor
LOCAL_CLANG := true
LOCAL_C_INCLUDES += \
    frameworks/native/include \
    frameworks/native/libs/ui/include
LOCAL_VENDOR_MODULE := true
LOCAL_PROPRIETARY_MODULE := true
LOCAL_MULTILIB := both
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libshim_camera_metadata
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := CameraMetadataShim.cpp
LOCAL_SHARED_LIBRARIES := libcamera_client libcamera_metadata libutils libcutils liblog
LOCAL_CFLAGS := -Wno-unused-parameter -Wno-non-virtual-dtor
LOCAL_CLANG := true
LOCAL_C_INCLUDES += \
    frameworks/av/include \
    system/media/camera/include
LOCAL_VENDOR_MODULE := true
LOCAL_PROPRIETARY_MODULE := true
LOCAL_MULTILIB := both
include $(BUILD_SHARED_LIBRARY)
