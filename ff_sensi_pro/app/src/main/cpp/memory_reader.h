#pragma once
#include <cstdint>
#include <cstring>
#include <string>
#include <android/log.h>
#include <fcntl.h>
#include <unistd.h>

#define LOG_TAG "FF_CHEAT_MEM"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,  LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

class MemoryReader {
public:
    bool m_initialized = false;
    pid_t m_pid = -1;

    bool init(pid_t pid) {
        m_pid = pid;
        m_initialized = true;
        LOGI("Memory Reader initialized for PID: %d", pid);
        return true;
    }

    bool readMem(uint32_t addr, void* out, size_t size) {
        if (!m_initialized) return false;
        char path[256];
        snprintf(path, sizeof(path), "/proc/%d/mem", m_pid);
        int fd = open(path, O_RDONLY);
        if (fd < 0) return false;
        ssize_t bytes = pread(fd, out, size, addr);
        close(fd);
        return bytes == static_cast<ssize_t>(size);
    }

    uint32_t readU32(uint32_t addr) {
        uint32_t v; readMem(addr, &v, 4); return v;
    }
    float readF32(uint32_t addr) {
        float v; readMem(addr, &v, 4); return v;
    }
    std::string readStr(uint32_t addr) {
        char b[128] = {0}; readMem(addr, b, 128); return std::string(b);
    }
};