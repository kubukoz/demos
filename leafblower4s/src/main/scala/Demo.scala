package leafblower4s

object Demo extends App {
  val lb = LeafBlower.instance

  println(lb.helloLeaf())
  val lang: lb.Language = lb.Language.make("scala")
  try println(lb.blowLeaf(lang))
  finally lb.freeLeaf(lang)
}
