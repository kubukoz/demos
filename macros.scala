import scala.quoted.*

// @annotation.implicitNotFound("${A} is not a union type")
trait IsUnion[A] {
  type LHS
  type RHS
  def isParent: ((LHS | RHS) =:= A)

}

object IsUnion {

  type Aux[A, L, R] = IsUnion[A] { type LHS = L; type RHS = R }

  transparent inline given derived[A]: IsUnion[A] = ${ deriveImpl[A] }

  // Slight variation on https://github.com/iRevive/union-derivation
  private def deriveImpl[A](
    using quotes: Quotes,
    t: Type[A],
  ): Expr[IsUnion[A]] = {
    import quotes.reflect.*
    val tpe: TypeRepr = TypeRepr.of[A]
    tpe.dealias match {
      case OrType(lhs, rhs) =>
        (lhs.asType, rhs.asType) match {
          case ('[l], '[r]) =>
            '{
              new IsUnion[A] {
                type LHS = l
                type RHS = r
                def isParent: ((l | r) =:= A) = scala.compiletime.summonInline[(l | r) =:= A]
              }
            }
        }
      case other => report.errorAndAbort(s"${tpe.show} is not a Union")
    }
  }

}
