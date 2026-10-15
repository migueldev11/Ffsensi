#pragma once
#include "config.h"
#include "memory_reader.h"
#include "math_utils.h"
#include <vector>
#include <string>

struct Target {
    Vec3 pos;
    float health;
    std::string name;
    int team;
    bool alive;
    float distance;
    Vec2 screenPos;
};

class CheatCore {
public:
    CheatConfig config;
    MemoryReader reader;
    int m_screenW, m_screenH;
    uint32_t m_gameBase = 0;

    bool init(pid_t pid, int screenWidth, int screenHeight);
    void tick(float currentAimX, float currentAimY);
    void setGameBase(uint32_t base);
    std::vector