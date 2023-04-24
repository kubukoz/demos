// This function needs to be called before invoking any methods defined in Scala
// Native. Returns 0 if successfull or non-zero in the otherwise It is used to
// setup GC, initialize instanstiable classes, etc.
int ScalaNativeInit(void);

void dupa();
