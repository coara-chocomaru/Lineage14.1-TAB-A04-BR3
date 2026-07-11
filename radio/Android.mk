LOCAL_PATH := $(call my-dir)

define add-a04br3-radio-image
include $(CLEAR_VARS)
LOCAL_MODULE := $(1)
LOCAL_MODULE_CLASS := ETC
LOCAL_MODULE_PATH := $(PRODUCT_OUT)
LOCAL_SRC_FILES := ../rootdir/$(1)
LOCAL_MODULE_STEM := $(1)
include $(BUILD_PREBUILT)
INSTALLED_RADIOIMAGE_TARGET += $(PRODUCT_OUT)/$(1)
endef

$(eval $(call add-a04br3-radio-image,logo.img))
$(eval $(call add-a04br3-radio-image,lk.img))
$(eval $(call add-a04br3-radio-image,tee.img))
$(eval $(call add-a04br3-radio-image,preloader.img))
$(eval $(call add-a04br3-radio-image,factory.img))
$(eval $(call add-a04br3-radio-image,twrp.img))
