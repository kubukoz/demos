//> using lib "org.typelevel::cats-core:2.10.0"
//> using option "-Ykind-projector:underscores"
// tuple instances /shrug
import cats.Invariant._
import cats.kernel.CommutativeMonoid
import cats.syntax.all.*
import cats.kernel.Semilattice
import cats.Applicative
import cats.syntax.nonEmptyTraverse
import cats.kernel.Monoid
import cats.kernel.Order
import scala.collection.immutable.SortedMap

case class MultiSet[A] private (
  impl: Map[A, Int]
) {

  def ++(
    another: MultiSet[A]
  ): MultiSet[A] = MultiSet(
    impl |+| another.impl
  )

}

object MultiSet {

  def singleton[A](
    a: A
  ): MultiSet[A] = MultiSet(Map(a -> 1))

  def empty[A]: MultiSet[A] = MultiSet(Map.empty)

  given [A]: CommutativeMonoid[MultiSet[A]] with {

    override def combine(
      x: MultiSet[A],
      y: MultiSet[A],
    ): MultiSet[A] = x ++ y

    override def empty: MultiSet[A] = MultiSet.empty
  }

}

trait Demo extends App {
  type Time

  given timeOrder: Order[Time]

  given HasFilter[Time] with {

    enum CustomFilter {
      case IsAfter(t: Time)
    }

    override def matches(f: CustomFilter, i: Time): Boolean =
      f match {
        case CustomFilter.IsAfter(t) => i > t
      }

  }

  def isAfter(t: Time): InputFilter[Time] = custom[Time](HasFilter[Time].CustomFilter.IsAfter(t))

  val example = gate(
    isAfter(??? : Time),
    empty,
  )

}

trait App {

  trait HasFilter[I] {

    type CustomFilter

    def matches(
      f: CustomFilter,
      i: I,
    ): Boolean

  }

  object HasFilter {
    def apply[I](using hf: HasFilter[I]): hf.type = hf
  }

  def custom[I]: (hf: HasFilter[I]) ?=> (hf.CustomFilter) => InputFilter[I]

  type Challenge[I, R, K]

  type InputFilter[I]

  type ClueState

  given clueStateMonoid: Semilattice[ClueState]

  def always[I]: InputFilter[I]
  def never[I]: InputFilter[I]

  // todo: impossible signature. this has to be different than that
  // def time[I, R, K]: Time => I

  // def isAfter: (
  //   Time,
  //   Time,
  // ) => Boolean

  // def afterTime: Time => InputFilter[Time] = ???
  // t =>
  //   custom[Time](new HasFilter[Time] {

  //     type CustomFilter[Time] = Time => Boolean
  //     override def matches(f: CustomFilter[Time], i: Time): Boolean = f(i)

  //   })(isAfter(t, _))

  // def timeout[I, R, K]: (
  //   Challenge[I, R, K],
  //   Time,
  // ) => Challenge[I, R, K]
  // can be derived as
  //  =
  //   (
  //     ch,
  //     t,
  //   ) => eitherC(gate(afterTime(t), empty), ch)

  def andF[I]: (
    InputFilter[I],
    InputFilter[I],
  ) => InputFilter[I]

  def orF[I]: (
    InputFilter[I],
    InputFilter[I],
  ) => InputFilter[I]

  def notF[I]: InputFilter[I] => InputFilter[I]

  def matches[I]: (
    InputFilter[I],
    I,
  ) => Boolean

  def completes[I, R, K]: (
    Challenge[I, R, K],
    List[I],
  ) => Option[List[I]]

  def getRewards[I, R, K]: (
    Challenge[I, R, K],
    List[I],
  ) => R

  // def pointOfInterest[I, R, K]: (
  //   Clue,
  //   Point,
  //   Distance,
  //   Reward,
  // ) => Challenge[I, R, K]

  def sub[K]: (List[K], List[K]) => List[K]

  def noClue[K]: List[K]

  def clue[I, R, K]: (
    List[K],
    Challenge[I, R, K],
  ) => Challenge[I, R, K]

  object Clue {
    def unapply[I, R, K](c: Challenge[I, R, K]): Option[(List[K], Challenge[I, R, K])] = ???
  }

  def seen: ClueState
  def completed: ClueState
  def failed: ClueState

  def getClues[I, R, K]: Challenge[I, R, K] => List[I] => SortedMap[List[K], ClueState]

  // def photo[I, R, K]: (
  //   Point,
  //   Photo,
  // ) => I

  // def photoWithin: (
  //   Point,
  //   Distance,
  //   Challenge,
  // ) => Challenge

  // def photoWithin[I, R, K]: (
  //   Point,
  //   Distance,
  // ) => InputFilter[I]

  // def locWithin[I, R, K]: (
  //   Point,
  //   Distance,
  // ) => InputFilter[I]

  def reward[I, R, K](
    reward: R
  ): Challenge[I, R, K]

  object Reward {
    def unapply[I, R, K](c: Challenge[I, R, K]): Option[R] = ???
  }

  def andThen[I, R, K]: (
    Challenge[I, R, K],
    Challenge[I, R, K],
  ) => Challenge[I, R, K]

  object AndThen {
    def unapply[I, R, K](c: Challenge[I, R, K]): Option[(Challenge[I, R, K], Challenge[I, R, K])] =
      ???
  }

  def empty[I, R, K]: Challenge[I, R, K]

  object Empty {
    def unapply[I, R, K](c: Challenge[I, R, K]): Boolean = ???
  }

  def bottom[I, R, K]: Challenge[I, R, K]

  def both[I, R, K]: (
    Challenge[I, R, K],
    Challenge[I, R, K],
  ) => Challenge[I, R, K]

  object Both {
    def unapply[I, R, K](c: Challenge[I, R, K]): Option[(Challenge[I, R, K], Challenge[I, R, K])] =
      ???
  }

  def eitherC[I, R, K]: (
    Challenge[I, R, K],
    Challenge[I, R, K],
  ) => Challenge[I, R, K]

  object EitherC {
    def unapply[I, R, K](c: Challenge[I, R, K]): Option[(Challenge[I, R, K], Challenge[I, R, K])] =
      ???
  }

  // def within: (
  //   Point,
  //   Point,
  //   Distance,
  // ) => Boolean

  // def above: (
  //   Altitude,
  //   Point,
  // ) => Boolean
  // def photoAbove: (
  //   Altitude,
  //   Challenge,
  // ) => Challenge

  def gate[I, R, K]: (
    InputFilter[I],
    Challenge[I, R, K],
  ) => Challenge[I, R, K]

  object Gate {
    def unapply[I, R, K](c: Challenge[I, R, K]): Option[(InputFilter[I], Challenge[I, R, K])] = ???
  }

  // def photoAbove[I, R, K]: Altitude => InputFilter[I]

  def isEmpty[I, R, K]: Challenge[I, R, K] => Boolean
  def isReward[I, R, K]: Challenge[I, R, K] => Boolean
  // def isPhoto[I, R, K]: I => Boolean

  def shorterOf[A]: (
    List[A],
    List[A],
  ) => List[A]

  type StepResult[R, K, A] =
    (
      (
        R,
        SortedMap[List[K], ClueState],
      ),
      A,
    )

  def step[I, R, K]: CommutativeMonoid[R] ?=> HasFilter[I] ?=> Order[K] ?=> (
    List[K],
    Option[I],
    Challenge[I, R, K],
  ) => StepResult[R, K, Challenge[I, R, K]]

  def pumpChallenge[I, R: CommutativeMonoid, K](
    c: Challenge[I, R, K]
  ): List[I] => (
    (R, SortedMap[List[K], ClueState]),
    Challenge[I, R, K],
  )

  def tellClue[R, K]: Map[List[K], ClueState] => StepResult[R, K, Unit]

  def findClues[I, R, K]: (List[K], Challenge[I, R, K]) => Map[List[K], ClueState] = {
    case (_, Empty)              => Map.empty
    case (kctx, Both(c1, c2))    => findClues(kctx, c1) |+| findClues(kctx, c2)
    case (kctx, EitherC(c1, c2)) => findClues(kctx, c1) |+| findClues(kctx, c2)
    case (_, Gate(_, _))         => Map.empty
    case (kctx, AndThen(c, _))   => findClues(kctx, c)
    case (_, Reward(_))          => Map.empty
    case (kctx, Clue(k, Empty))  => Map((kctx |+| k) -> completed)
    case (kctx, Clue(k, c)) =>
      Map((kctx |+| k) -> seen) |+|
        findClues(kctx |+| k, c)
  }

  object laws {

    def `reward/mempty`[R: Monoid]() = reward(Monoid[R].empty) == empty

    def `reward/mappend`[R: Monoid](r1: R, r2: R) =
      reward(r1 |+| r2) ==
        andThen(reward(r1), reward(r2))

    // def `inside point of interest`[I, R, K](
    //   c: Clue,
    //   poi: Point,
    //   d: Distance,
    //   r: Reward,
    //   p: Point,
    //   pic: Photo,
    //   is: List[I],
    // ): Boolean =
    //   within(poi, p, d) ==> {
    //     getRewards(pointOfInterest(c, poi, d, r), photo(p, pic) :: is) ==
    //       MultiSet.singleton(r)
    //   }

    // def `outside point of interest`[I, R, K](
    //   c: Clue,
    //   poi: Point,
    //   d: Distance,
    //   r: Reward,
    //   p: Point,
    //   pic: Photo,
    //   is: List[I],
    // ): Boolean =
    //   !within(poi, p, d) ==> {
    //     getRewards(pointOfInterest(c, poi, d, r), photo(p, pic) :: is) ==
    //       getRewards(pointOfInterest(c, poi, d, r), is)
    //   }

    // // def `unmatching point of interest`[I, R, K](
    // //   c: Clue,
    // //   poi: Point,
    // //   d: Distance,
    // //   r: Reward,
    // //   i: I,
    // //   is: List[I],
    // // ): Boolean =
    // //   !isPhoto(i) ==> {
    // //     getRewards(pointOfInterest(c, poi, d, r), i :: is) ==
    // //       getRewards(pointOfInterest(c, poi, d, r), is)
    // //   }

    def `getRewards/clue`[I, R, K](
      k: List[K],
      c: Challenge[I, R, K],
    ) =
      (getRewards[I, R, K](clue(k, c), _)) <->
        (getRewards[I, R, K](c, _))

    def `getRewards/reward`[I, R, K](
      r: R,
      is: List[I],
    ) = getRewards(reward(r), is) == r

    // def `matches/photoWithin`[I: HasFilter](
    //   p: Point,
    //   d: Distance,
    //   ph: Photo,
    //   poi: Point,
    // ) =
    //   matches(photoWithin(p, d), photo(poi, ph)) ==
    //     within(p, poi, d)

    // def `matches/photoWithin doesn't match for non-photos`[I, R, K](
    //   p: Point,
    //   d: Distance,
    //   ph: Photo,
    //   poi: Point,
    //   in: I,
    // ) =
    //   !isPhoto(in) ==>
    //     !matches(photoWithin(p, d), in)

    // def `matches/photoAbove`(
    //   alt: Altitude,
    //   p: Point,
    //   ph: Photo,
    //   poi: Point,
    // ) =
    //   matches(photoAbove(alt), photo(poi, ph)) ==
    //     above(alt, poi)

    // def `matches/photoAbove doesn't match for non-photos`[I, R, K](
    //   alt: Altitude,
    //   in: I,
    // ) =
    //   !isPhoto(in) ==>
    //     !matches(photoAbove(alt), in)

    def `matches/always`[I, R, K](
      i: I
    ) = matches(always, i) == true

    def `matches/never`[I, R, K](
      i: I
    ) = matches(never, i) == false

    def `matches/andF`[I](
      f1: InputFilter[I],
      f2: InputFilter[I],
      i: I,
    ) =
      matches(andF(f1, f2), i) ==
        (matches(f1, i) && matches(f2, i))

    def `matches/orF`[I](
      f1: InputFilter[I],
      f2: InputFilter[I],
      i: I,
    ) =
      matches(orF(f1, f2), i) ==
        (matches(f1, i) || matches(f2, i))

    def `matches/notF`[I](
      f: InputFilter[I],
      i: I,
    ) =
      matches(notF(f), i) ==
        !matches(f, i)

    // def `getRewards/photoWithin`(
    //   poi: Point,
    //   p: Point,
    //   pic: Photo,
    //   d: Distance,
    //   c: Challenge,
    //   is: List[I],
    // ) =
    //   within(poi, p, d) ==> {
    //     getRewards(photoWithin(poi, d, c), photo(p, pic) :: is) ==
    //       getRewards(c, is)
    //   }

    // def `pointOfInterest_`(
    //   c: Clue,
    //   p: Point,
    //   d: Distance,
    //   r: Reward,
    // ) =
    //   pointOfInterest(c, p, d, r) ==
    //     clue(c, photoWithin(p, d, reward(r)))

    def `getRewards/gate`[I, R: CommutativeMonoid, K](
      f: InputFilter[I],
      i: I,
      c: Challenge[I, R, K],
      is: List[I],
    ) =
      matches(f, i) ==> {
        getRewards(gate(f, c), i :: is) ==
          getRewards(c, is)
      }

    def `getRewards/gate unmatched`[I, R: CommutativeMonoid, K](
      f: InputFilter[I],
      i: I,
      c: Challenge[I, R, K],
      is: List[I],
    ) =
      !matches(f, i) ==> {
        getRewards(gate(f, c), i :: is) ==
          getRewards(gate(f, c), is)
      }

    def `getRewards/gate empty`[I, R: CommutativeMonoid, K](
      f: InputFilter[I],
      c: Challenge[I, R, K],
    ) =
      getRewards(gate(f, c), Nil) ==
        MultiSet.empty

    // def `pointOfInterest_`(
    //   c: Clue,
    //   p: Point,
    //   d: Distance,
    //   r: Reward,
    // ) =
    //   pointOfInterest(c, p, d, r) ==
    //     clue(c, gate(photoWithin(p, d), reward(r)))

    def `bothCommutative`[I, R, K](
      c1: Challenge[I, R, K],
      c2: Challenge[I, R, K],
    ) = both(c1, c2) == both(c2, c1)

    def `bothAssociative`[I, R, K](
      c1: Challenge[I, R, K],
      c2: Challenge[I, R, K],
      c3: Challenge[I, R, K],
    ) = both(c1, both(c2, c3)) == both(both(c1, c2), c3)

    // def `bothIdempotent`(
    //   c: Challenge
    // ) = both(c, c) == c

    // exercise 5
    def `getRewards/both`[I, R: CommutativeMonoid, K](
      c1: Challenge[I, R, K],
      c2: Challenge[I, R, K],
      is: List[I],
    ) =
      // contradiction: order of rewards depends on the order of params to both?
      getRewards(both(c1, c2), is) ==
        (getRewards(c1, is) |+| getRewards(c2, is))

    def `getRewards/empty`[I, R: CommutativeMonoid, K](
      is: List[I]
    ) = getRewards(empty, is) == CommutativeMonoid[R].empty

    def `getRewards/andThen`[I, R: CommutativeMonoid, K](
      c1: Challenge[I, R, K],
      c2: Challenge[I, R, K],
      is1: List[I],
      is2: List[I],
    ) =
      (completes(c1, is1) == Some(is2)) ==> {
        getRewards(andThen(c1, c2), is1) ==
          (getRewards(c1, is1) |+| getRewards(c2, is2))
      }

    def `getRewards/andThen incomplete`[I, R: CommutativeMonoid, K](
      c1: Challenge[I, R, K],
      c2: Challenge[I, R, K],
      is: List[I],
    ) =
      (completes(c1, is) == None) ==> {
        getRewards(andThen(c1, c2), is) ==
          getRewards(c1, is)
      }

    def `bothIdentity`[I, R, K](
      c: Challenge[I, R, K]
    ) =
      (both(c, empty) == c) &&
        // commutativity implies this too
        (c == both(empty, c))

    def `andThen/gate`[I, R, K](
      f: InputFilter[I],
      c1: Challenge[I, R, K],
      c2: Challenge[I, R, K],
    ) =
      andThen(gate(f, c1), c2) ==
        gate(f, andThen(c1, c2))

    def `andThen/associative`[I, R, K](
      c1: Challenge[I, R, K],
      c2: Challenge[I, R, K],
      c3: Challenge[I, R, K],
    ) =
      andThen(c1, andThen(c2, c3)) ==
        andThen(andThen(c1, c2), c3)

    def `andThen/identity`[I, R, K](
      c: Challenge[I, R, K]
    ) =
      andThen(empty, c) == c &&
        andThen(c, empty) == c

    def `falsified - complete/andThen`[I, R, K](
      f: InputFilter[I],
      c1: Challenge[I, R, K],
      c2: Challenge[I, R, K],
      is1: List[I],
      is2: List[I],
    ) = {
      def completes: (
        Challenge[I, R, K],
        List[I],
      ) => Boolean = ???

      // A
      completes(andThen(c1, c2), is1 ++ is2) ==
        completes(c1, is1) && completes(c2, is2)

      // B
      andThen(gate(f, c1), c2) == gate(f, andThen(c1, c2))

      // Prove A contradicts B
      // in A, substitute c1 with gate(f,c1)
      // A:
      completes(andThen(gate(f, c1), c2), is1 ++ is2) ==
        completes(gate(f, c1), is1) && completes(c2, is2)
      //
      // substitute andThen(gate(f, c1), c2) == gate(f, andThen(c1, c2))

      completes(gate(f, andThen(c1, c2)), is1 ++ is2) ==
        completes(gate(f, c1), is1) && completes(c2, is2)

      // at this point I just have prose:
      // for some "is1", if is1 is not enough to pass c1 but (is1++is2) is enough, LHS may be true but RHS will be false
      // e.g. is1=Nil and is2 is the concatenation of both
      // is that sufficient proof?
    }

    def `completes/empty`[I, R: CommutativeMonoid, K](
      is: List[I]
    ) = completes(empty, is) == Some(is)

    def `completes/reward`[I, R: CommutativeMonoid, K](
      r: R,
      is: List[I],
    ) = completes(reward(r), is) == Some(is)

    def `completes/clue`[I, R: CommutativeMonoid, K](
      k: List[K],
      c: Challenge[I, R, K],
      is: List[I],
    ) = completes(clue(k, c), is) == completes(c, is)

    def `completes/both`[I, R: CommutativeMonoid, K](
      c1: Challenge[I, R, K],
      c2: Challenge[I, R, K],
      is: List[I],
    ) =
      completes(both(c1, c2), is) == {
        for {
          i1 <- completes(c1, is)
          i2 <- completes(c2, is)
        } yield shorterOf(i1, i2)
      }

    def `step/both`[I: HasFilter, R: CommutativeMonoid, K: Order](
      kctx: List[K],
      i: Option[I],
      c1: Challenge[I, R, K],
      c2: Challenge[I, R, K],
    ) =
      step(kctx, i, both(c1, c2)) ==
        (step(kctx, i, c1), step(kctx, i, c2)).mapN(both)

    def `step/empty`[I: HasFilter, R: CommutativeMonoid, K: Order](
      kctx: List[K],
      i: Option[I],
    ) = step(kctx, i, empty) == Applicative[StepResult[R, K, _]].pure(empty)

    def `step/reward`[I: HasFilter, R: CommutativeMonoid, K: Order](
      kctx: List[K],
      i: Option[I],
      r: R,
    ) = step(kctx, i, reward(r)) == Applicative[StepResult[R, K, _]].pure(empty)

    def `step/gate`[I: HasFilter, R: CommutativeMonoid, K: Order](
      kctx: List[K],
      i: I,
      f: InputFilter[I],
      c: Challenge[I, R, K],
    ) =
      matches(f, i) ==> {
        step(kctx, Some(i), gate(f, c)) == step(kctx, None, c)
      }

    def `step/gate unmatched`[I: HasFilter, R: CommutativeMonoid, K: Order](
      kctx: List[K],
      i: I,
      f: InputFilter[I],
      c: Challenge[I, R, K],
    ) =
      !matches(f, i) ==> {
        step(kctx, Some(i), gate(f, c)) == Applicative[StepResult[R, K, _]].pure(gate(f, c))
      }

    def `step/gate nothing`[I: HasFilter, R: CommutativeMonoid, K: Order](
      kctx: List[K],
      f: InputFilter[I],
      c: Challenge[I, R, K],
    ) = step(kctx, None, gate(f, c)) == Applicative[StepResult[R, K, _]].pure(gate(f, c))

    def `step/andThen complete`[I: HasFilter, R: CommutativeMonoid, K: Order](
      kctx: List[K],
      i: Option[I],
      c1: Challenge[I, R, K],
      c2: Challenge[I, R, K],
      r: (R, SortedMap[List[K], ClueState]),
    ) = // if this input completes the c1 challenge with rewards `r`
      step(kctx, i, c1) == (r, empty) ==> {
        // then stepping once through the composition is the same as...
        step(kctx, i, andThen(c1, c2)) ==
          // getting that reward and whatever the reward is for no input of c2
          (r, step(kctx, None, c2)).flatten
      }

    def `step/andThen incomplete`[I: HasFilter, R: CommutativeMonoid, K: Order](
      kctx: List[K],
      i: Option[I],
      c1: Challenge[I, R, K],
      c2: Challenge[I, R, K],
      r: MultiSet[R],
    ) =
      // if this input completes with a non-empty challenge
      (!isEmpty(step(kctx, i, c1)._2)) ==> {
        // then stepping once through the composition is the same as...
        step(kctx, i, andThen(c1, c2)) ==
          // stepping once through c1, keeping the rest and passing that to an andThen
          step(kctx, i, c1).fmap(andThen(_, c2))
      }

    // def `step/timeout matched`[I, R, K](
    //   kctx: Clue,
    //   c: Challenge[I, R, K],
    //   t: Time,
    //   cutoff: Time,
    // ) =
    //   isAfter(cutoff, t) ==> {
    //     step[I, R, K](kctx, Some(time(t)), timeout(c, cutoff)) ==
    //       Applicative[StepResult].pure(empty)
    //   }

    // def `step/timeout unmatched`[I, R, K](
    //   kctx: Clue,
    //   c: Challenge[I, R, K],
    //   t: Time,
    //   cutoff: Time,
    // ) =
    //   !isAfter(t, cutoff) ==> {
    //     step(kctx, Some(time(t)), timeout(c, cutoff)) == step(kctx, None, c)
    //   }

    // def `step/timeout nothing`[I, R, K](
    //   kctx: Clue,
    //   c: Challenge[I, R, K],
    //   t: Time,
    //   i: Option[I],
    // ) = step(kctx, None, timeout(c, t)) == step(kctx, None, c)

    def `pumpChallenge law`[I: HasFilter, R: CommutativeMonoid, K: Order](
      c: Challenge[I, R, K]
    ) =
      pumpChallenge(c) <->
        (_.map(_.some).prepended(None).foldM(c)((c, i) => step(noClue[K], i, c)))

    def `getRewardsNew`[I, R: CommutativeMonoid, K: Order](
      c: Challenge[I, R, K]
    ) = (getRewards[I, R, K](c, _)) <-> pumpChallenge(c).andThen(_._1)

    def `completesNew`[I, R: CommutativeMonoid, K](
      c: Challenge[I, R, K]
    ) = (completes[I, R, K](c, _)) <-> pumpChallenge(c).andThen(_._2).andThen(isEmpty)

    def `completes/gate`[I, R: CommutativeMonoid, K](
      f: InputFilter[I],
      i: I,
      c: Challenge[I, R, K],
      is: List[I],
    ) =
      matches(f, i) ==> {
        completes(gate(f, c), i :: is) ==
          Some(is)
      }

    def `completes/gate unmatched`[I, R: CommutativeMonoid, K](
      f: InputFilter[I],
      i: I,
      c: Challenge[I, R, K],
      is: List[I],
    ) =
      !matches(f, i) ==> {
        completes(gate(f, c), i :: is) ==
          completes(gate(f, c), is)
      }

    def `completes/gate empty`[I, R: CommutativeMonoid, K](
      f: InputFilter[I],
      c: Challenge[I, R, K],
    ) = completes(gate(f, c), Nil) == None

    def `completes/andThen`[I, R: CommutativeMonoid, K](
      c1: Challenge[I, R, K],
      c2: Challenge[I, R, K],
      is: List[I],
    ) =
      completes(andThen(c1, c2), is) ==
        completes(c1, is).flatMap(completes(c2, _))

    def `shorterOf/concat`[A](
      prefix: List[A],
      suffix: List[A],
    ) = shorterOf(prefix ++ suffix, prefix) == prefix

    def `shorterOf/commutative`[A](
      l1: List[A],
      l2: List[A],
    ) = shorterOf(l1, l2) == shorterOf(l2, l1)

    def `shorterOf/associative`[A](
      l1: List[A],
      l2: List[A],
      l3: List[A],
    ) = shorterOf(shorterOf(l1, l2), l3) == shorterOf(l1, shorterOf(l2, l3))

    // falsified: doesn't form a monoid
    // def `shorterOf/empty`[A](
    //   l: List[A]
    // ) = shorterOf(l, Nil) == Nil

    def `both/andThen/reward`[I, R, K](
      r: R,
      c1: Challenge[I, R, K],
      c2: Challenge[I, R, K],
    ) =
      both(andThen(reward(r), c1), c2) ==
        andThen(reward(r), both(c1, c2))

    def `eitherC:associative`[I, R, K](
      c1: Challenge[I, R, K],
      c2: Challenge[I, R, K],
      c3: Challenge[I, R, K],
    ) = eitherC(eitherC(c1, c2), c3) == eitherC(c1, eitherC(c2, c3))

    def `eitherC:commutative`[I, R, K](
      c1: Challenge[I, R, K],
      c2: Challenge[I, R, K],
    ) = eitherC(c1, c2) == eitherC(c2, c1)

    // todo: confirm?
    def `eitherC:identity`[I, R, K](
      c1: Challenge[I, R, K]
    ) = eitherC(c1, bottom) == c1

    def `eitherC/andThen/reward`[I, R, K](
      r: R,
      c1: Challenge[I, R, K],
      c2: Challenge[I, R, K],
    ) =
      eitherC(andThen(reward(r), c1), c2) ==
        andThen(reward(r), eitherC(c1, c2))

    def `eitherC/empty`[I, R, K](
      c: Challenge[I, R, K]
    ) =
      !isReward(c) ==> {
        eitherC(empty, c) == empty
      }

    def `bottomLaw`[I, R, K](
      c: Challenge[I, R, K]
    ) = bottom == gate(never, c)

    def `clue/sub`[K](k1: List[K], k2: List[K]) =
      (clue(sub(k1, k2), _)) <->
        ((clue(k1, _)).compose(clue(k2, _)))

    def `clue/noClue`[I, R, K](c: Challenge[I, R, K]) = clue(noClue, c) == c

    def `step/clue/empty`[I: HasFilter, R: CommutativeMonoid, K: Order](
      kctx: List[K],
      i: Option[I],
      k: List[K],
    ) =
      step(kctx, i, clue(k, empty)) ==
        tellClue(Map((kctx |+| k) -> completed)) *>
        Applicative[StepResult[R, K, _]].pure(empty)

    def `step/clue non-empty`[I: HasFilter, R: CommutativeMonoid, K: Order](
      kctx: List[K],
      i: Option[I],
      k: List[K],
      c: Challenge[I, R, K],
    ) =
      !isEmpty(c) ==> {
        step(kctx, i, clue(k, c)) ==
          tellClue(Map(sub(kctx, k) -> seen)) *>
          step(sub(kctx, k), i, c).map(clue(k, _))
      }

    def `step/eitherC empty`[I: HasFilter, R: CommutativeMonoid, K: Order](
      kctx: List[K],
      i: Option[I],
      c1: Challenge[I, R, K],
      c2: Challenge[I, R, K],
      `c2'`: Challenge[I, R, K],
    ) = {
      step(kctx, i, c1)._2 == empty &&
      step(kctx, i, c2)._2 == `c2'`
    } ==> {
      step(kctx, i, eitherC(c1, c2)) ==
        tellClue(findClues(kctx, `c2'`).fmap(seenToFailed)) *>
        step(kctx, i, c2) *>
        step(kctx, i, c1)
    }

    def `step/eitherC non-empty`[I: HasFilter, R: CommutativeMonoid, K: Order](
      kctx: List[K],
      i: Option[I],
      c1: Challenge[I, R, K],
      c2: Challenge[I, R, K],
      `c2'`: Challenge[I, R, K],
    ) = {
      !isEmpty(step(kctx, i, c1)._2) &&
      !isEmpty(step(kctx, i, c2)._2)
    } ==> {
      step(kctx, i, eitherC(c1, c2)) ==
        (
          step(kctx, i, c1),
          step(kctx, i, c2),
        ).mapN(eitherC)
    }

  }

  def seenToFailed: ClueState => ClueState

  object exercises {

    def `reduce getRewards(clue (c (reward r))) to its simplest form via algebraic manipulation`[
      R: CommutativeMonoid,
      K,
    ](
      c: List[K],
      r: R,
    ) = {
      getRewards(clue(c, reward(r)), ???)
      //  via `getRewards/clue`
      getRewards(reward(r), ???)
      // via `getRewards/reward`
      CommutativeMonoid[R].empty
    }

    // def `e2: what's the intuitive understanding + do stated semantics agree with you`(
    //   p1: Point,
    //   d1: Distance,
    //   p2: Point,
    //   d2: Distance,
    //   r: Reward,
    //   pho1: Photo,
    //   pho2: Photo,
    // ) = {
    //   getRewards(
    //     gate(photoWithin(p1, d1), gate(photoWithin(p2, d2), reward(r))),
    //     photo(p1, pho1) :: photo(p2, pho2) :: Nil,
    //   )
    //   // getRewards/gate matched
    //   // matches(f, i) ==> {
    //   //   getRewards(gate(f, c), i :: is) ==
    //   //     getRewards(c, is)
    //   // }
    //   // yes matches: getRewards(gate(f, c), i :: is) == getRewards(c, is)
    //   getRewards(
    //     gate(photoWithin(p2, d2), reward(r)),
    //     photo(p2, pho2) :: Nil,
    //   )
    //   // again: getRewards(gate(f, c), i :: is) == getRewards(c, is)
    //   getRewards(reward(r), Nil)
    //   MultiSet.singleton(r)

    // }

    // def `e3: encode challenge to walk around the block twice, clockwise`[I, R, K](
    //   p1: Point,
    //   p2: Point,
    //   p3: Point,
    //   d1: Distance,
    //   d2: Distance,
    //   d3: Distance,
    //   r: Reward,
    // ): Challenge[I, R, K] = {
    //   val oneRound: Challenge[I, R, K] => Challenge[I, R, K] = (gate[I, R, K](locWithin(p1, d1), _))
    //     .compose(gate[I, R, K](locWithin(p2, d2), _))
    //     .compose(gate[I, R, K](locWithin(p3, d3), _))

    //   oneRound.compose(oneRound).apply(reward(r))
    // }

    def `e4: prove`[I, R, K](
      c1: Challenge[I, R, K],
      c2: Challenge[I, R, K],
      c3: Challenge[I, R, K],
      c4: Challenge[I, R, K],
    ) = {
      val desired = both(c1, both(c2, both(c3, c4)))
      both(both(c1, c2), both(c3, c4))
      // by associativity
      both(c1, both(c2, both(c3, c4)))
    }

    def `e: reduce eitherC(reward r1) empty to its simplest form`[R](
      r: R
    ) = {
      eitherC(reward(r), empty)
      // via andThen identity: andThen(c, empty) == c (c == reward(r))
      eitherC(andThen(reward(r), empty), empty)
      // via eitherC/andThen/reward: c1=empty, c2=empty
      andThen(reward(r), eitherC(empty, empty))
      // via eitherC/empty: not a reward, so empty
      andThen(reward(r), empty)
      // via andThen/identity: andThen(c, empty) == c
      reward(r)
    }

  }

}

def forall[A, B](
  f: A => Law[B]
): Law[B] = ???

case class Law[A](
  lhs: A,
  rhs: A,
)

extension [A](
  a: A
)

  def <->(
    another: A
  ): Law[A] = ???

extension (
  b: Boolean
)

  def ==>(
    another: Boolean
  ) =
    if (b)
      another
    else
      true
