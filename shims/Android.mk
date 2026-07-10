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
	$(hide) nm -D $< | grep -q "T _ZN7android13GraphicBufferC1Ejjij" \
		|| (echo "FATAL: GraphicBuffer ctor symbol not exported as T" && exit 1)
	$(hide) nm -D $< | grep -q "T _ZN7android14CameraMetadata14getTagFromNameEPKcPKNS_19VendorTagDescriptorEPj" \
		|| (echo "FATAL: CameraMetadata::getTagFromName symbol not exported as T" && exit 1)
	$(hide) touch $@

ALL_DEFAULT_INSTALLED_MODULES += $(SHIM_VERIFY_STAMP)

SHIM_COPY_SYS32 := $(TARGET_OUT)/lib/libshim_mt8167compat.so
SHIM_COPY_SYS64 := $(TARGET_OUT)/lib64/libshim_mt8167compat.so

$(SHIM_COPY_SYS32): $(LOCAL_BUILT_MODULE) | $(ACP)
	$(hide) mkdir -p $(dir $@)
	$(hide) $(ACP) -fp $(LOCAL_BUILT_MODULE) $@

$(SHIM_COPY_SYS64): $(LOCAL_BUILT_MODULE) | $(ACP)
	$(hide) mkdir -p $(dir $@)
	$(hide) $(ACP) -fp $(LOCAL_BUILT_MODULE) $@

ALL_DEFAULT_INSTALLED_MODULES += $(SHIM_COPY_SYS32) $(SHIM_COPY_SYS64)

CAM_UTILS_SRC_32 := vendor/sts/a04br3/proprietary/vendor/lib/libcam_utils.so
CAM_UTILS_SRC_64 := vendor/sts/a04br3/proprietary/vendor/lib64/libcam_utils.so

CAM_UTILS_OUT_32 := $(TARGET_OUT_VENDOR)/lib/libcam_utils.so
CAM_UTILS_OUT_64 := $(TARGET_OUT_VENDOR)/lib64/libcam_utils.so

define patch-needed
$(hide) mkdir -p $(dir $(2))
$(hide) cp -f $(1) $(2).tmp
$(hide) chmod u+w $(2).tmp
$(hide) patchelf --add-needed libshim_mt8167compat.so $(2).tmp
$(hide) readelf -d $(2).tmp | grep -q libshim_mt8167compat.so \
	|| (echo "FATAL: patchelf did not add NEEDED entry to $(2)" && exit 1)
$(hide) mv -f $(2).tmp $(2)
endef

ifneq ($(wildcard $(CAM_UTILS_SRC_32)),)
$(CAM_UTILS_OUT_32): $(CAM_UTILS_SRC_32) libshim_mt8167compat
	$(call patch-needed,$(CAM_UTILS_SRC_32),$(CAM_UTILS_OUT_32))
ALL_DEFAULT_INSTALLED_MODULES += $(CAM_UTILS_OUT_32)
else
$(warning [shim] libcam_utils.so 32bit not found at $(CAM_UTILS_SRC_32))
endif

ifneq ($(wildcard $(CAM_UTILS_SRC_64)),)
$(CAM_UTILS_OUT_64): $(CAM_UTILS_SRC_64) libshim_mt8167compat
	$(call patch-needed,$(CAM_UTILS_SRC_64),$(CAM_UTILS_OUT_64))
ALL_DEFAULT_INSTALLED_MODULES += $(CAM_UTILS_OUT_64)
else
$(warning [shim] libcam_utils.so 64bit not found at $(CAM_UTILS_SRC_64))
endif

RUNTIME_LIB32 := $(TARGET_OUT)/lib/libandroid_runtime.so
RUNTIME_LIB64 := $(TARGET_OUT)/lib64/libandroid_runtime.so

RUNTIME_PATCH_STAMP32 := $(intermediates)/runtime_patch_32.stamp
RUNTIME_PATCH_STAMP64 := $(intermediates)/runtime_patch_64.stamp

define patch-needed-inplace
$(hide) if readelf -d $(1) | grep -q libshim_mt8167compat.so; then \
	echo "[shim] $(1) already patched"; \
else \
	chmod u+w $(1) && patchelf --add-needed libshim_mt8167compat.so $(1); \
	echo "[shim] patched $(1)"; \
fi
$(hide) readelf -d $(1) | grep -q libshim_mt8167compat.so \
	|| (echo "FATAL: failed to patch $(1)" && exit 1)
endef

$(RUNTIME_PATCH_STAMP32): $(SHIM_COPY_SYS32) $(SHIM_COPY_SYS64)
	$(hide) if [ -f $(RUNTIME_LIB32) ]; then \
		$(call patch-needed-inplace,$(RUNTIME_LIB32)) \
	else \
		echo "FATAL: $(RUNTIME_LIB32) does not exist yet" && exit 1; \
	fi
	$(hide) touch $@

$(RUNTIME_PATCH_STAMP64): $(SHIM_COPY_SYS32) $(SHIM_COPY_SYS64)
	$(hide) if [ -f $(RUNTIME_LIB64) ]; then \
		$(call patch-needed-inplace,$(RUNTIME_LIB64)) \
	else \
		echo "FATAL: $(RUNTIME_LIB64) does not exist yet" && exit 1; \
	fi
	$(hide) touch $@

droidcore: $(RUNTIME_PATCH_STAMP32) $(RUNTIME_PATCH_STAMP64)
