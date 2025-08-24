FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

# # Установка зависимостей
RUN apt-get update && apt-get install -y \
    wget unzip curl git qemu-kvm libvirt-daemon-system libvirt-clients \
    libgl1-mesa-dev xvfb x11vnc novnc websockify socat \
    openjdk-21-jdk unzip mc \
	&& apt-get clean \
    && rm -rf /var/lib/apt/lists/*

ENV ANDROID_SDK_ROOT=/opt/android-sdk
ENV PATH=$ANDROID_SDK_ROOT/emulator:$ANDROID_SDK_ROOT/platform-tools:$ANDROID_SDK_ROOT/cmdline-tools/latest/bin:$PATH
COPY cmdline-tools.zip /tmp/
COPY sdk.sh /tmp/

WORKDIR /tmp
RUN /tmp/sdk.sh

WORKDIR /opt/android-sdk

# Порт для VNC
EXPOSE 5900
EXPOSE 6080

# RUN echo "Europe/Prague" "no" | ./cmdline-tools/bin/avdmanager create avd \
RUN avdmanager create avd \
    -n aaos_emulator \
    -k "system-images;android-33;android-automotive;x86_64" \
    --device "automotive_1024p_landscape"

# Запуск эмулятора и VNC через Xvfb
CMD ["/bin/bash", "-c", "Xvfb :1 -screen 0 1080x1920x24 & \
    x11vnc -display :1 -forever -nopw & \
    websockify -D --web=/usr/share/novnc/ 6080 localhost:5900 & \
    emulator -avd aaos_emulator -partition-size 2048 -wipe-data -noaudio -no-boot-anim -gpu swiftshader_indirect -no-window -verbose"]

# CMD ["/bin/bash"]