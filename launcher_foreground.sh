image_url="https://upload.wikimedia.org/wikipedia/commons/thumb/c/c6/Sign-check-icon.png/768px-Sign-check-icon.png"

# Extract the filename from the URL
filename="ic_launcher_foreground.webp"

script_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

local_file_path="$script_dir/app/src/main/res"


# Download the image using curl
curl -O "$image_url"

# Check if the download was successful
if [ $? -ne 0 ]; then
  echo "Failed to download the image."
  exit 1
fi

# Resize and save to mipmap-xxxhdpi
convert "$filename" -resize 432x432 mipmap-xxxhdpi/"$filename"

# Resize and save to mipmap-xxhdpi
convert "$filename" -resize 324x324 mipmap-xxhdpi/"$filename"

# Resize and save to mipmap-xhdpi
convert "$filename" -resize 216x216 mipmap-xhdpi/"$filename"

# Resize and save to mipmap-mdpi
convert "$filename" -resize 108x108 mipmap-mdpi/"$filename"

# Resize and save to mipmap-hdpi
convert "$filename" -resize 162x162 mipmap-hdpi/"$filename"

echo "Image resized and saved to mipmap directories."