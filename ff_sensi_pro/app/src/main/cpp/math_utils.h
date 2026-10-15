#pragma once
#include <cmath>

struct Vec2 { float x, y; };
struct Vec3 { float x, y, z; };

namespace Math {
    inline float Dot(const Vec3& a, const Vec3& b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }
    inline Vec3 Cross(const Vec3& a, const Vec3& b) {
        return { a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x };
    }
    inline Vec3 Normalize(const Vec3& v) {
        float len = std::sqrt(Math::Dot(v, v));
        if (len == 0) return {0, 0, 0};
        return {v.x / len, v.y / len, v.z / len};
    }
    inline float Length(const Vec3& v) {
        return std::sqrt(Math::Dot(v, v));
    }
    inline Vec3 Sub(const Vec3& a, const Vec3& b) {
        return {a.x - b.x, a.y - b.y, a.z - b.z};
    }
}