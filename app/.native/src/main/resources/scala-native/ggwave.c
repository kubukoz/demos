#include <string.h>
#include "ggwave.h"

void __sn_wrap_ggwave_ggwave_getDefaultParameters(ggwave_Parameters *____return) {
  ggwave_Parameters ____ret = ggwave_getDefaultParameters();
  memcpy(____return, &____ret, sizeof(ggwave_Parameters));
}


ggwave_Instance __sn_wrap_ggwave_ggwave_init(ggwave_Parameters *parameters) {
 return ggwave_init(*parameters);
};


