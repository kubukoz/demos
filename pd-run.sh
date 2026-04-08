#!/usr/bin/env bash
set -euo pipefail

if [[ $# -lt 1 ]]; then
  echo "Usage: $0 <command> [args...]" >&2
  exit 1
fi

DEVICE=$(ls /dev/cu.usbmodemPDU1_* 2>/dev/null | head -n1 || true)

if [[ -z "${DEVICE}" ]]; then
  echo "No Playdate device found" >&2
  exit 1
fi

exec 3<>"${DEVICE}"
printf '%s\r\n' "$*" >&3
(cat <&3) &
CAT_PID=$!
sleep 1.5
kill "${CAT_PID}" 2>/dev/null || true
exec 3<&-
