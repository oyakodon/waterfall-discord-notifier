#!/bin/bash

if [ $# -lt 2 ]; then
  echo "Usage: $(basename $0) dist version build" 1>&2
  exit 1
fi

dist=$1
version=$2
build=$3

if [ $dist = "papermc" ]; then
  if [ -z BUILD ]; then
    echo "no BUILD set." 1>&2
    exit 1
  fi

  URL="https://papermc.io/api/v2/projects/paper/versions/${version}/builds/${build}/downloads/paper-${version}-${build}.jar"
elif [ $dist = "waterfall" ]; then
  if [ -z BUILD ]; then
    echo "no BUILD set." 1>&2
    exit 1
  fi

  URL="https://papermc.io/api/v2/projects/waterfall/versions/${version}/builds/${build}/downloads/waterfall-${version}-${build}.jar"
elif [ $dist = "forge" ]; then
  if [ -z BUILD ]; then
    echo "no BUILD set." 1>&2
    exit 1
  fi

  forge_base_url="https://files.minecraftforge.net/maven/net/minecraftforge/forge"

  echo "Use ${build} build version."
  case $build in
    "recommended" | "latest")
      build=$(curl -fsSL "${forge_base_url}/promotions_slim.json" \
        | jq -r ".promos[\"${version}-${build}\"]")
      ;;
  esac
  
  echo "> ${build}"
  URL="${forge_base_url}/${version}-${build}/forge-${version}-${build}-installer.jar"
elif [ $dist = "velocity" ]; then
  URL="https://versions.velocitypowered.com/download/${version}.jar"
fi

if [ -z URL ]; then
  echo "invalid dist." 1>&2
  exit 1
fi

echo "Start download: $URL"
curl -o server.jar -fL $URL -#

if [ $dist = "forge" ]; then
  echo "Start install server..."
  installer="installer_forge-${version}-${build}.jar"
  mv "forge-${version}.jar" $installer
  java -jar $installer --installServer
  mv forge-${version}-*.jar server.jar
fi

echo "Download completed."
