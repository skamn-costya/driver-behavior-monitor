#!/bin/bash

mkdir -p offline-apt

# List of packages you need
echo "openjdk-21-jdk qemu-kvm libvirt-daemon-system libvirt-clients xvfb x11vnc novnc websockify socat unzip mc" > packages.txt

# Update cache
apt-get update

# Download all packages and dependencies
apt-get install --download-only --reinstall -y $(cat packages.txt)

# Copy downloaded .deb files to the folder
cp -v /var/cache/apt/archives/*.deb ./offline-apt

tar czf offline-apt.tar.gz offline-apt

rm -fR ./offline-apt
