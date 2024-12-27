package leafblower4s

trait LeafBlower {
  type Language

  trait LanguageFunctions {
    def make(languageName: String): Language
  }

  val Language: LanguageFunctions
  def helloLeaf(): Int
  def blowLeaf(language: Language): Int
  def freeLeaf(language: Language): Unit
}

object LeafBlower {
  val instance: LeafBlower = LeafBlowerPlatform.instance
}
