package leafblower4s

trait LeafBlower {
  type Language

  trait LanguageFunctions {
    def make(languageName: String): Language
  }

  val Language: LanguageFunctions
  def blowLeaf(leaf: Language): Int
  def freeLeaf(leaf: Language): Unit
}

object LeafBlower {
  val instance: LeafBlower = LeafBlowerPlatform.instance
}
