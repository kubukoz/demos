package leafblower4s

import scala.scalanative.unsafe.Ptr
import scala.scalanative.unsafe.extern
import scala.scalanative.unsafe.link
import scala.scalanative.posix.dlfcn
import scala.scalanative.unsafe.*

object LeafBlowerPlatform {

  def instance: LeafBlower =
    new LeafBlower {

      object types {}

      import types.*
      type Language = Ptr[Unit]
      def helloLeaf(): Int = bindings.hello_leaf()
      def blowLeaf(leaf: Language): Int = bindings.blow_leaf(leaf)
      def freeLeaf(leaf: Language): Unit = bindings.free_leaf(leaf)

      val Language: LanguageFunctions =
        new LanguageFunctions {

          def make(languageName: String): Language = Zone {
            // need to figure this out (why do we say lib here?)

            val name = s"lib/libleaf-blower-$languageName.dylib"
            val handle: Ptr[?] = dlfcn.dlopen(
              toCString(name),
              dlfcn.RTLD_LAZY,
            )
            if (handle == null)
              sys.error(s"sorry, lib is null. error: ${fromCString(dlfcn.dlerror())}")

            val functionName = toCString(s"${languageName}_leaf")
            val func = dlfcn.dlsym(handle, functionName)
            if (func == null)
              sys.error("sorry, func is null")

            CFuncPtr.fromPtr[CFuncPtr0[Ptr[Unit]]](func).apply()
          }

        }

    }

}

@extern
@link("leaf-blower")
object bindings {
  def hello_leaf(): Int = extern
  def blow_leaf(language: Ptr[Unit]): Int = extern
  def free_leaf(language: Ptr[Unit]): Unit = extern
}
