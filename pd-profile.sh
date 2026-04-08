#!/usr/bin/env bash
set -euo pipefail

FOLDED=0
if [[ "${1:-}" == "--folded" ]]; then
  FOLDED=1
  shift
fi

DURATION="${1:-5}"
INTERVAL="${2:-10}"
ELF="${ELF:-modules/game/target/device-native-3/playdate/Source/pdex.elf}"

if [[ ! -f "${ELF}" ]]; then
  echo "ELF not found: ${ELF}" >&2
  echo "Set ELF=<path> to override." >&2
  exit 1
fi

DEVICE=$(ls /dev/cu.usbmodemPDU1_* 2>/dev/null | head -n1 || true)
if [[ -z "${DEVICE}" ]]; then
  echo "No Playdate device found" >&2
  exit 1
fi

ADDR2LINE="${ADDR2LINE:-arm-none-eabi-addr2line}"

TMP=$(mktemp)
trap 'rm -f "${TMP}"' EXIT

echo "Profiling for ${DURATION}s at ${INTERVAL}ms intervals..." >&2

exec 3<>"${DEVICE}"
printf 'trace %s\r\n' "${INTERVAL}" >&3
(cat <&3 > "${TMP}") &
CAT_PID=$!
sleep "${DURATION}"
printf 'stoptrace\r\n' >&3
sleep 0.3
kill "${CAT_PID}" 2>/dev/null || true
exec 3<&-

# Extract just the hex addresses from ~tr: lines
ADDRS=$(grep -oE '~tr: 0x[0-9a-fA-F]+' "${TMP}" | awk '{print $2}')
TOTAL=$(printf '%s\n' "${ADDRS}" | grep -c . || true)

if [[ "${TOTAL}" -eq 0 ]]; then
  echo "No samples collected." >&2
  exit 1
fi

echo "Collected ${TOTAL} samples" >&2
echo >&2

# Count, sort by frequency, then symbolicate the unique addresses
printf '%s\n' "${ADDRS}" | sort | uniq -c | sort -rn | while read -r count addr; do
  func=$("${ADDR2LINE}" -f -C -e "${ELF}" "${addr}" 2>/dev/null | head -n1 || echo "??")
  if [[ -z "${func}" || "${func}" == "??" ]]; then
    func="firmware_${addr}"
  fi
  if [[ "${FOLDED}" -eq 1 ]]; then
    # Folded stacks format: frame1;frame2 count
    # We have flat samples, so just one frame per "stack"
    printf '%s %s\n' "${func}" "${count}"
  else
    printf '%6d  %s  %s\n' "${count}" "${addr}" "${func}"
  fi
done
