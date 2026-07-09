#define LOG_TAG "libshim_gui"

#include <ui/GraphicBuffer.h>

namespace android {

GraphicBuffer::GraphicBuffer(uint32_t inWidth, uint32_t inHeight,
        PixelFormat inFormat, uint32_t inUsage) :
    GraphicBuffer(inWidth, inHeight, inFormat, inUsage, std::string("<Unknown>"))
{
}
}
