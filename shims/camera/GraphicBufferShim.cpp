#define LOG_TAG "libshim_mt8167compat_gui"

#include <ui/GraphicBuffer.h>
#include <string>

namespace android {

// _ZN7android13GraphicBufferC1Ejjij
GraphicBuffer::GraphicBuffer(uint32_t inWidth, uint32_t inHeight,
        PixelFormat inFormat, uint32_t inUsage) :
    GraphicBuffer(inWidth, inHeight, inFormat, inUsage, std::string("<Unknown>"))
{
}

}
