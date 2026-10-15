cmake_minimum_required(VERSION 3.22)
project(ffcheat_native CXX)

add_library(
    ffcheat
    SHARED
    cheat_core.cpp
)

target_link_libraries(
    ffcheat
    log
    android
    GLESv2
)