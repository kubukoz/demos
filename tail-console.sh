#!/usr/bin/env bash
set -euo pipefail

DEVICE=$(ls /dev/cu.usbmodemPDU1_* 2>/dev/null | head -n1 || true)

if [[ -z "${DEVICE}" ]]; then
  echo "No Playdate device found (looked for /dev/cu.usbmodemPDU1_*)" >&2
  exit 1
fi

echo "Tailing ${DEVICE} (Ctrl-C to quit)" >&2

# Open the serial port for read+write so the device-side endpoint stays active,
# enable echo on the command interface, then stream everything that comes back.
exec 3<>"${DEVICE}"
printf 'echo on\r\n' >&3
cat <&3
