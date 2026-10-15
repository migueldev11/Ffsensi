#pragma once
#include <cstdint>
#include <string>

namespace FF {
    // Offsets do Free Fire (Exemplos - Atualize via Scanner)
    constexpr uint32_t OFFSET_LOCAL_PLAYER = 0x1A2F4;
    constexpr uint32_t OFFSET_ENTITY_LIST = 0x1B3C0;
    constexpr uint32_t OFFSET_PAWN = 0x0C;
    constexpr uint32_t OFFSET_POS_X = 0x110;
    constexpr uint32_t OFFSET_POS_Y = 0x114;
    constexpr uint32_t OFFSET_POS_Z = 0x118;
    constexpr uint32_t OFFSET_HEALTH = 0x11C;
    constexpr uint32_t OFFSET_NAME = 0x120;
    constexpr uint32_t OFFSET_TEAM = 0x124;
    constexpr uint32_t OFFSET_ALIVE = 0x128;
    constexpr uint32_t OFFSET_CAMERA = 0x130;
    constexpr uint32_t OFFSET_CAM_RIGHT = 0x20;
    constexpr uint32_t OFFSET_CAM_UP = 0x40;
    constexpr uint32_t OFFSET_CAM_FWD = 0x60;
    constexpr uint32_t OFFSET_CAM_POS = 0x80;
    constexpr uint32_t OFFSET_CAM_FOV = 0xA0;
}

struct CheatConfig {
    bool aimbotEnabled = true;
    float aimbotFov = 120.0f;
    float aimbotSmoothness = 0.15f;
    float aimbotHeadshotOffset = 45.0f;
    bool espEnabled = true;
    int espMode = 0; // 0: Box, 1: Skeleton, 2: Line
    bool espShowHealth = true;
    bool espShowName = true;
    bool recoilEnabled = true;
    float recoilStrength = 0.5f;
};