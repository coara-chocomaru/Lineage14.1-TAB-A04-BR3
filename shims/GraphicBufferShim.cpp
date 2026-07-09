#include <ui/GraphicBuffer.h>
#include <utils/Log.h>
#include <string>
#include <new>

extern "C"
__attribute__((visibility("default")))
void _ZN7android13GraphicBufferC1Ejjij(
        android::GraphicBuffer *thiz,
        uint32_t inWidth,
        uint32_t inHeight,
        android::PixelFormat inFormat,
        uint32_t inUsage) {
    new (thiz) android::GraphicBuffer(
            inWidth, inHeight, inFormat, inUsage, std::string("<Unknown>"));
}
