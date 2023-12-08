image_url="$1"

script_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

local_file_path="$script_dir/res/mipmap-xxxhdpi/downloaded_image.png"

if [ -e "$local_file_path" ]; then
    echo "File already exists. Replacing..."
    rm "$local_file_path"
fi

mkdir -p "$(dirname "$local_file_path")"

curl -o "$local_file_path" "$image_url"

if [ $? -eq 0 ]; then
    echo "Download successful. Image saved at: $local_file_path"
else
    echo "Download failed. Please check the URL and try again."
fi
