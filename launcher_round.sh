image_url="$1"

# Extract the filename from the URL
filename="ic_launcher_round.webp"

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
convert "$filename" -resize 192x192 mipmap-xxxhdpi/"$filename"

# Resize and save to mipmap-xxhdpi
convert "$filename" -resize 144x144 mipmap-xxhdpi/"$filename"

# Resize and save to mipmap-xhdpi
convert "$filename" -resize 96x96 mipmap-xhdpi/"$filename"

# Resize and save to mipmap-mdpi
convert "$filename" -resize 48x48 mipmap-mdpi/"$filename"

# Resize and save to mipmap-hdpi
convert "$filename" -resize 72x72 mipmap-hdpi/"$filename"

echo "Image resized and saved to mipmap directories."