#!/bin/bash

# apt-get update && apt-get install -y openjdk-21-jdk 

export ANDROID_SDK_ROOT=/data/android-sdk
export PATH=$ANDROID_SDK_ROOT/cmdline-tools/bin:$PATH

unzip /data/cmdline-tools.zip -d $ANDROID_SDK_ROOT

yes | sdkmanager --licenses --sdk_root=$ANDROID_SDK_ROOT

$ANDROID_SDK_ROOT/cmdline-tools/bin/sdkmanager --verbose --sdk_root=$ANDROID_SDK_ROOT \
    --channel=0 \
    --install "platform-tools" \
    "platforms;android-33" \
    "system-images;android-33;android-automotive;x86_64" \
    "emulator"

tar czf android-sdk.tar.gz android-sdk
