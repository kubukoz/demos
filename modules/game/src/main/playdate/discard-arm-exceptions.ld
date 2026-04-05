/* Discard ARM exception handling sections to keep ELF section count under 65535 (pdc limitation). */
/* These are unused on bare-metal Playdate (no C++ exceptions, no libunwind). */
SECTIONS {
  /DISCARD/ : {
    *(.ARM.exidx*)
    *(.ARM.extab*)
  }
}
