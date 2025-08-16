docker container stop can-emulator
docker container rm can-emulator
docker image rm can-emulator
docker build -t can-emulator -f Dockerfile.can-emulator .
