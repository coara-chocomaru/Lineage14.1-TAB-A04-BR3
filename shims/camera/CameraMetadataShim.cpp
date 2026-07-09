#define LOG_TAG "libshim_mt8167compat_camera"

#include <camera/CameraMetadata.h>
#include <system/camera_vendor_tags.h>
#include <utils/StrongPointer.h>

namespace android {

// _ZN7android14CameraMetadata14getTagFromNameEPKcPKNS_19VendorTagDescriptorEPj
status_t CameraMetadata::getTagFromName(const char *name,
        const VendorTagDescriptor* vTags, uint32_t *tag) {
    if (vTags == NULL) {
        sp<VendorTagDescriptor> vTagDesc =
                VendorTagDescriptor::getGlobalVendorTagDescriptor();
        vTags = vTagDesc.get();
    }
    (void)vTags;
    return CameraMetadata::getTagFromName(name, tag);
}

}
