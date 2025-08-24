# Use the official Ubuntu 22.04 base image from Docker Hub.
# This provides a stable and minimal foundation for your server.
FROM ubuntu:22.04

# Set metadata for the image. This is a good practice for organization.
LABEL maintainer="Your Name <you@example.com>"
LABEL description="A clean, minimal Ubuntu Server 22.04 Docker image."

# Set environment variables if needed. Here, we'll set one to avoid prompts during package installation.
ENV DEBIAN_FRONTEND=noninteractive

# Update the package lists and install a few basic utilities.
# The '&&' and '\' are used to combine multiple commands into a single RUN instruction.
# This reduces the number of layers in the final image, making it smaller and faster to build.
# The 'rm' command at the end cleans up the apt cache to further reduce image size.
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        curl \
        wget \
        git \
		mc \
		unzip \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Expose a volume to persist data.
# The VOLUME instruction creates a mount point with the specified name or path
# and marks it as holding externally mounted volumes from the native host or other containers.
# This is essential for storing important data that should not be part of the container's image.
# For example, a database's data or user-uploaded files.
VOLUME /data

# Set the working directory inside the container.
# This is where subsequent commands (like COPY or RUN) will be executed by default.
WORKDIR /data

# Define the default command to run when the container starts.
# We'll use a shell here, which is useful for interactive work or for a base image.
# If your application is a specific command, you would replace '/bin/bash' with it.
CMD ["/bin/bash"]