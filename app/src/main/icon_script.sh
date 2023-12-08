#!/bin/bash

# Get the URL from the command-line argument
image_url="https://upload.wikimedia.org/wikipedia/commons/thumb/c/c6/Sign-check-icon.png/768px-Sign-check-icon.png"

# Directory where the script is located
script_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Local file path to save the image in res/mipmap-xxxhdpi
local_file_path="$script_dir/res/mipmap-xxxhdpi/downloaded_image.png"

# Check if the file already exists
if [ -e "$local_file_path" ]; then
    echo "File already exists. Replacing..."
    rm "$local_file_path"
fi

# Create the directory if it doesn't exist
mkdir -p "$(dirname "$local_file_path")"

# Download the image using curl
curl -o "$local_file_path" "$image_url"

# Check if the download was successful
if [ $? -eq 0 ]; then
    echo "Download successful. Image saved at: $local_file_path"
else
    echo "Download failed. Please check the URL and try again."
fi
