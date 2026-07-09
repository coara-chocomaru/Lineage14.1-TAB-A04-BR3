#define LOG_TAG "libshim_camera_metadata"

#include <camera/CameraMetadata.h>
#include <system/camera_vendor_tags.h>

namespace android {

status_t CameraMetadata::getTagFromName(const char *name,
        const VendorTagDescriptor* vTags, uint32_t *tag) {
    if (vTags == NULL) {
        sp<VendorTagDescriptor> vTagDesc =
                VendorTagDescriptor::getGlobalVendorTagDescriptor();
        vTags = vTagDesc.get();
    }
    return CameraMetadata::getTagFromName(name, tag);
}
}
