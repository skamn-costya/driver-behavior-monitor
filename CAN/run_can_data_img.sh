# docker run --rm -p 5555:5555 -p 5000:5000 --cap-add=NET_ADMIN can-emulator
docker run --network=host -h can-emulator -it can-emulator

# docker run --rm -it --network=host -v ~/.android:/root/.android can-emulator
