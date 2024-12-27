package leafblower4s

import com.sun.jna.*
import scala.annotation.nowarn

private object LeafBlowerPlatform {

  private val LIBRARY: LeafBlowerLibrary =
    try Native
        .load(
          "leaf-blower",
          classOf[LeafBlowerLibrary],
        )
    catch {
      case e: UnsatisfiedLinkError => throw new Exception("Couldn't load leaf-blower", e)
    }

  def instance: LeafBlower =
    new LeafBlower {

      trait LanguageWrapper {
        def lang: LeafBlowerLibrary.Language
      }

      type Language = LanguageWrapper

      val Language: LanguageFunctions =
        new LanguageFunctions {

          def make(languageName: String): LanguageWrapper = {
            val library = NativeLibrary.getInstance(s"leaf-blower-$languageName")

            val function = library.getFunction(s"${languageName}_leaf");

            val langg = function
              .invoke(classOf[LeafBlowerLibrary.Language], Array())
              .asInstanceOf[LeafBlowerLibrary.Language]

            new LanguageWrapper {
              def lang: LeafBlowerLibrary.Language = {
                // but we need to keep a reference to the library for... reasons
                // probably related to, but not quite the same, as:
                // https://github.com/java-native-access/jna/pull/1378
                // basically, segfaults.
                library.hashCode(): @nowarn("msg=unused value")
                langg
              }
            }
          }

        }

      def helloLeaf(): Int = LIBRARY.hello_leaf()

      def blowLeaf(language: LanguageWrapper): Int = LIBRARY.blow_leaf(language.lang)

      def freeLeaf(language: LanguageWrapper): Unit = LIBRARY.free_leaf(language.lang)
    }

}
