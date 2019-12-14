ROOT_PATH := $(call my-dir)
SDL_PATH := $(ROOT_PATH)/src/SDL2
SDL_IMAGE_PATH := $(ROOT_PATH)/src/SDL2_image
LIBPNG_PATH := $(SDL_IMAGE_PATH)/external/libpng-1.6.37

include $(SDL_PATH)/Android.mk
include $(SDL_IMAGE_PATH)/Android.mk
include $(ROOT_PATH)/src/SDL2_mixer/Android.mk
include $(ROOT_PATH)/src/SDL2_ttf/Android.mk
include $(ROOT_PATH)/src/amiberry/Android.mk