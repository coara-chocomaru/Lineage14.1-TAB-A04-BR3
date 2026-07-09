LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := libshim_mt8167compat
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := \
    GraphicBufferShim.cpp \
    CameraMetadataShim.cpp
LOCAL_SHARED_LIBRARIES := \
    libui \
    libutils \
    libcutils \
    liblog \
    libcamera_client \
    libcamera_metadata
LOCAL_CFLAGS := \
    -Wno-unused-parameter \
    -Wno-non-virtual-dtor \
    -fvisibility=default \
    -fno-data-sections \
    -fno-function-sections
LOCAL_LDFLAGS := -Wl,--no-gc-sections
LOCAL_CLANG := true
LOCAL_C_INCLUDES += \
    frameworks/native/include \
    frameworks/native/libs/ui/include \
    frameworks/av/include \
    system/media/camera/include
LOCAL_VENDOR_MODULE := true
LOCAL_PROPRIETARY_MODULE := true
LOCAL_MULTILIB := both
include $(BUILD_SHARED_LIBRARY)

SHIM_VERIFY_STAMP := $(intermediates)/shim_verify.stamp

$(SHIM_VERIFY_STAMP): $(LOCAL_BUILT_MODULE)
	$(hide) echo "[shim-verify] checking exported symbols in $<"
	$(hide) nm -D $< | grep -q "T _ZN7android13GraphicBufferC1Ejjij" \
		|| (echo "FATAL: GraphicBuffer ctor symbol not exported as T" && exit 1)
	$(hide) nm -D $< | grep -q "T _ZN7android14CameraMetadata14getTagFromNameEPKcPKNS_19VendorTagDescriptorEPj" \
		|| (echo "FATAL: CameraMetadata::getTagFromName symbol not exported as T" && exit 1)
	$(hide) touch $@

ALL_DEFAULT_INSTALLED_MODULES += $(SHIM_VERIFY_STAMP)

CAM_UTILS_SRC_32 := vendor/sts/a04br3/proprietary/vendor/lib/libcam_utils.so
CAM_UTILS_SRC_64 := vendor/sts/a04br3/proprietary/vendor/lib64/libcam_utils.so

CAM_UTILS_OUT_32 := $(TARGET_OUT_VENDOR)/lib/libcam_utils.so
CAM_UTILS_OUT_64 := $(TARGET_OUT_VENDOR)/lib64/libcam_utils.so

define patch-cam-utils
$(hide) mkdir -p $(dir $(2))
$(hide) cp -f $(1) $(2).tmp
$(hide) chmod u+w $(2).tmp
$(hide) patchelf --add-needed libshim_mt8167compat.so $(2).tmp
$(hide) readelf -d $(2).tmp | grep -q libshim_mt8167compat.so \
	|| (echo "FATAL: patchelf did not add NEEDED entry to $(2)" && exit 1)
$(hide) mv -f $(2).tmp $(2)
$(hide) echo "[shim] patched $(2) with libshim_mt8167compat.so"
endef

ifneq ($(wildcard $(CAM_UTILS_SRC_32)),)
$(CAM_UTILS_OUT_32): $(CAM_UTILS_SRC_32) libshim_mt8167compat
	$(call patch-cam-utils,$(CAM_UTILS_SRC_32),$(CAM_UTILS_OUT_32))
ALL_DEFAULT_INSTALLED_MODULES += $(CAM_UTILS_OUT_32)
else
$(warning [shim] libcam_utils.so 32bit not found at $(CAM_UTILS_SRC_32))
endif

ifneq ($(wildcard $(CAM_UTILS_SRC_64)),)
$(CAM_UTILS_OUT_64): $(CAM_UTILS_SRC_64) libshim_mt8167compat
	$(call patch-cam-utils,$(CAM_UTILS_SRC_64),$(CAM_UTILS_OUT_64))
ALL_DEFAULT_INSTALLED_MODULES += $(CAM_UTILS_OUT_64)
else
$(warning [shim] libcam_utils.so 64bit not found at $(CAM_UTILS_SRC_64))
endif
