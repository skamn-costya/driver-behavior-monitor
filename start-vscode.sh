#!/usr/bin/env bash
set -e

WORKSPACE_FILE="./projects.code-workspace"
PORTABLE_DIR="$PWD/.vscode-portable"
EXTENSIONS_FILE="./.vscode/extensions.json"

# === Генерация workspace ===
generate_workspace() {
	echo "[INFO] Generating workspace file: $WORKSPACE_FILE"

	folders_json=""
	for dir in */ ; do
		[[ "$dir" == ".vscode-portable/" ]] && continue
		[[ "$dir" == ".git/" ]] && continue
		[[ "$dir" == ".vscode/" ]] && continue
		path="${dir%/}"
		folders_json="${folders_json}{\"path\": \"${path}\"},"
	done

	folders_json="[${folders_json%,}]"

	cat > "$WORKSPACE_FILE" <<EOF
{
	"folders": $folders_json,
	"settings": {
		"typescript.tsdk": "\${workspaceFolder}/node_modules/typescript/lib"
	}
}
EOF
}

# === Чтение recommendations (grep+sed) ===
read_recommendations() {
	sed -n '/"recommendations"/,/\]/p' "$EXTENSIONS_FILE" \
		| grep -oE '"[^"]+\.[^"]+"' \
		| tr -d '"'
}

# === Чтение unwantedRecommendations (grep+sed) ===
read_unwanted() {
	sed -n '/"unwantedRecommendations"/,/\]/p' "$EXTENSIONS_FILE" \
		| grep -oE '"[^"]+\.[^"]+"' \
		| tr -d '"'
}

# === Генерация workspace при необходимости ===
if [[ ! -f "$WORKSPACE_FILE" ]]; then
	generate_workspace
else
	echo "[INFO] Workspace file already exists: $WORKSPACE_FILE"
fi

# === Создание портативных папок ===
mkdir -p "$PORTABLE_DIR/extensions" "$PORTABLE_DIR/user-data"

# === Установка рекомендованных расширений ===
echo "[INFO] Installing recommended extensions from $EXTENSIONS_FILE..."
for ext in $(read_recommendations); do
	if ! code \
			--extensions-dir "$PORTABLE_DIR/extensions" \
			--user-data-dir "$PORTABLE_DIR/user-data" \
			--list-extensions | grep -q "$ext"; then
		echo "	-> Installing $ext"
		code --install-extension "$ext" \
				 --extensions-dir "$PORTABLE_DIR/extensions" \
				 --user-data-dir "$PORTABLE_DIR/user-data" >/dev/null
	else
		echo "	-> Already installed: $ext"
	fi
done

# === Удаление нежелательных расширений ===
echo "[INFO] Removing unwanted extensions..."
for ext in $(read_unwanted); do
	if code \
			--extensions-dir "$PORTABLE_DIR/extensions" \
			--user-data-dir "$PORTABLE_DIR/user-data" \
			--list-extensions | grep -q "$ext"; then
		echo "	-> Uninstalling $ext"
		code --uninstall-extension "$ext" \
				 --extensions-dir "$PORTABLE_DIR/extensions" \
				 --user-data-dir "$PORTABLE_DIR/user-data" >/dev/null
	else
		echo "	-> Not installed (skipped): $ext"
	fi
done

# === Запуск VSCode ===
echo "[INFO] Starting VSCode with workspace: $WORKSPACE_FILE"
code \
	--extensions-dir "$PORTABLE_DIR/extensions" \
	--user-data-dir "$PORTABLE_DIR/user-data" \
	"$WORKSPACE_FILE"
