package leafblower4s

object Demo extends App {
  val lb = LeafBlower.instance

  val lang = lb.Language.make("scala")
  try println(lb.blowLeaf(lang))
  finally lb.freeLeaf(lang)
}
