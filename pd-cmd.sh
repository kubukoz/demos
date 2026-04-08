#!/usr/bin/env bash
set -euo pipefail

if [[ $# -lt 1 ]]; then
  echo "Usage: $0 <command> [args...]" >&2
  echo "Example: $0 help" >&2
  exit 1
fi

DEVICE=$(ls /dev/cu.usbmodemPDU1_* 2>/dev/null | head -n1 || true)

if [[ -z "${DEVICE}" ]]; then
  echo "No Playdate device found (looked for /dev/cu.usbmodemPDU1_*)" >&2
  exit 1
fi

printf '%s\r\n' "$*" > "${DEVICE}"
