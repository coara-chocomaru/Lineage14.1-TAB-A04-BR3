#define LOG_TAG "libshim_mt8167compat_camera"

#include <camera/CameraMetadata.h>
#include <camera/VendorTagDescriptor.h>
#include <utils/Log.h>
#include <utils/String8.h>
#include <utils/StrongPointer.h>
#include <cstring>

using namespace android;

extern "C"
__attribute__((visibility("default")))
status_t _ZN7android14CameraMetadata14getTagFromNameEPKcPKNS_19VendorTagDescriptorEPj(
        const char *name,
        const VendorTagDescriptor *vTags,
        uint32_t *tag) {

    if (name == NULL || tag == NULL) {
        return BAD_VALUE;
    }

    sp<VendorTagDescriptor> localVTags;
    if (vTags == NULL) {
        localVTags = VendorTagDescriptor::getGlobalVendorTagDescriptor();
        vTags = localVTags.get();
    }

    if (vTags == NULL) {
        ALOGE("%s: no VendorTagDescriptor available, cannot resolve '%s'",
                __FUNCTION__, name);
        return NAME_NOT_FOUND;
    }
    const char *lastDot = strrchr(name, '.');
    if (lastDot == NULL) {
        ALOGE("%s: '%s' is not a fully qualified tag name", __FUNCTION__, name);
        return NAME_NOT_FOUND;
    }

    String8 section(name, lastDot - name);
    String8 tagName(lastDot + 1);

    status_t res = vTags->lookupTag(tagName, section, tag);
    if (res != OK) {
        ALOGE("%s: failed to resolve vendor tag '%s' (section='%s', tag='%s')",
                __FUNCTION__, name, section.string(), tagName.string());
    }
    return res;
}
