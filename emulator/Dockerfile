FROM ubuntu:22.04

# Установка зависимостей
RUN apt-get update && apt-get install -y \
    wget unzip curl git qemu-kvm libvirt-daemon-system libvirt-clients \
    libgl1-mesa-dev xvfb x11vnc novnc websockify socat \
    openjdk-11-jdk

# Android SDK
ENV ANDROID_SDK_ROOT=/opt/android-sdk
RUN mkdir -p $ANDROID_SDK_ROOT
WORKDIR $ANDROID_SDK_ROOT

RUN wget https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip -O cmdline-tools.zip \
    && unzip cmdline-tools.zip -d cmdline-tools \
    && rm cmdline-tools.zip

ENV PATH=$ANDROID_SDK_ROOT/cmdline-tools/cmdline-tools/bin:$PATH

RUN yes | sdkmanager --licenses
RUN sdkmanager "platform-tools" "emulator" "system-images;android-33;google_apis;x86_64"

# Создаем виртуальное устройство
RUN echo "no" | avdmanager create avd -n aaos -k "system-images;android-33;google_apis;x86_64" --force

# Порт для VNC
EXPOSE 5900
EXPOSE 6080

# Запуск эмулятора и VNC через Xvfb
CMD Xvfb :1 -screen 0 1080x1920x24 & \
    x11vnc -display :1 -forever -nopw & \
    websockify -D --web=/usr/share/novnc/ 6080 localhost:5900 & \
    emulator -avd aaos -noaudio -no-boot-anim -gpu swiftshader_indirect -no-window -verbose
