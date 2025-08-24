#!/bin/sh

mkdir -p $ANDROID_SDK_ROOT/cmdline-tools

unzip cmdline-tools.zip -d $ANDROID_SDK_ROOT/cmdline-tools
mv "$ANDROID_SDK_ROOT/cmdline-tools/cmdline-tools" "$ANDROID_SDK_ROOT/cmdline-tools/latest"

yes | sdkmanager --licenses --sdk_root=$ANDROID_SDK_ROOT

sdkmanager --verbose --sdk_root=$ANDROID_SDK_ROOT \
    --channel=0 \
    --install "platform-tools" \
    "platforms;android-33" \
    "system-images;android-33;android-automotive;x86_64" \
    "emulator"

