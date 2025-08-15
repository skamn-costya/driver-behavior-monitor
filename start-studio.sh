#!/bin/bash
set -e

# Путь к Android Studio
AS_BIN="/opt/android-studio/bin/studio.sh"

# Портативная папка для настроек
BASE="$PWD/.android-studio-portable"
# PLUGINS_DIR="$BASE/.plugins"
# mkdir -p "$PLUGINS_DIR"

# # Список плагинов
# PLUGINS=(
# 	org.jetbrains.kotlin
# 	com.google.gct.core
# 	com.intellij.docker
# 	com.dborisenko.idea.plugin.dbnavigator
# 	mallowigi.json.parser
# 	org.plantuml.idea
# 	com.mallowigi
# 	com.tabnine.intellij
# 	idea.plugin.protoeditor
# )

# # Скачиваем плагины из JetBrains Marketplace
# echo "Downloading plugins..."
# for plugin in "${PLUGINS[@]}"; do
# 	echo "Downloading $plugin..."
# 	curl -sL "https://plugins.jetbrains.com/plugin/download?pluginId=$plugin&updateId=latest" \
# 		-o "$PLUGINS_DIR/$plugin.zip" || echo "Failed to download $plugin"
# done

# # Распаковываем все zip-файлы
# echo "Unpacking plugins..."
# for zip in "$PLUGINS_DIR"/*.zip; do
# 	unzip -o "$zip" -d "$PLUGINS_DIR"
# done

# Запускаем Android Studio с портативными настройками
"$AS_BIN" "$PWD/android"
#   --user-data-dir "$BASE"/.user \
#   --system "$BASE/.system" \
#   --plugins "$PLUGINS_DIR" \
