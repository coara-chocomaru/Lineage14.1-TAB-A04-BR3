#include <ui/GraphicBuffer.h>
#include <string>

namespace android {

__attribute__((visibility("default")))
__attribute__((used))
GraphicBuffer::GraphicBuffer(uint32_t inWidth, uint32_t inHeight,
        PixelFormat inFormat, uint32_t inUsage) :
    GraphicBuffer(inWidth, inHeight, inFormat, inUsage, std::string("<Unknown>"))
{
}
}
