#include <camera/CameraMetadata.h>
#include <system/camera_vendor_tags.h>
#include <utils/StrongPointer.h>

namespace android {

__attribute__((visibility("default")))
__attribute__((used))
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
