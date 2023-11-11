//> using lib "org.typelevel::cats-core:2.10.0"
// tuple instances /shrug
import cats.Invariant._
import cats.kernel.CommutativeMonoid
import cats.syntax.all.*
import cats.kernel.Semilattice
import cats.Applicative
import cats.syntax.nonEmptyTraverse
import cats.kernel.Monoid

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

trait App {
  type Challenge
  type Reward
  type Input

  type Clue

  given clueMonoid: Monoid[Clue]

  type Point
  type Distance
  type Photo
  type Altitude
  type Time

  type InputFilter

  type ClueState

  given clueStateMonoid: Semilattice[ClueState]

  def always: InputFilter
  def never: InputFilter

  def time: Time => Input

  def isAfter: (
    Time,
    Time,
  ) => Boolean

  def afterTime: Time => InputFilter

  def timeout: (
    Challenge,
    Time,
  ) => Challenge
  // can be derived as
  //  =
  //   (
  //     ch,
  //     t,
  //   ) => eitherC(gate(afterTime(t), empty), ch)

  def andF: (
    InputFilter,
    InputFilter,
  ) => InputFilter

  def orF: (
    InputFilter,
    InputFilter,
  ) => InputFilter

  def notF: InputFilter => InputFilter

  def matches: (
    InputFilter,
    Input,
  ) => Boolean

  def completes: (
    Challenge,
    List[Input],
  ) => Option[List[Input]]

  def getRewards: (
    Challenge,
    List[Input],
  ) => MultiSet[Reward]

  def pointOfInterest: (
    Clue,
    Point,
    Distance,
    Reward,
  ) => Challenge

  def hint: String => Clue
  def noClue: Clue
  def sub: (Clue, Clue) => Clue

  def toList: Clue => List[String]

  def clue: (
    Clue,
    Challenge,
  ) => Challenge

  object Clue {
    def unapply(c: Challenge): Option[(Clue, Challenge)] = ???
  }

  def seen: ClueState
  def completed: ClueState
  def failed: ClueState

  def getClues: Challenge => List[Input] => Map[Clue, ClueState]

  def photo: (
    Point,
    Photo,
  ) => Input

  // def photoWithin: (
  //   Point,
  //   Distance,
  //   Challenge,
  // ) => Challenge

  def photoWithin: (
    Point,
    Distance,
  ) => InputFilter

  def locWithin: (
    Point,
    Distance,
  ) => InputFilter

  def reward(
    reward: Reward
  ): Challenge

  object Reward {
    def unapply(c: Challenge): Option[Reward] = ???
  }

  def andThen: (
    Challenge,
    Challenge,
  ) => Challenge

  object AndThen {
    def unapply(c: Challenge): Option[(Challenge, Challenge)] = ???
  }

  def empty: Challenge

  object Empty {
    def unapply(c: Challenge): Boolean = ???
  }

  def bottom: Challenge

  def both: (
    Challenge,
    Challenge,
  ) => Challenge

  object Both {
    def unapply(c: Challenge): Option[(Challenge, Challenge)] = ???
  }

  def eitherC: (
    Challenge,
    Challenge,
  ) => Challenge

  object EitherC {
    def unapply(c: Challenge): Option[(Challenge, Challenge)] = ???
  }

  def within: (
    Point,
    Point,
    Distance,
  ) => Boolean

  def above: (
    Altitude,
    Point,
  ) => Boolean
  // def photoAbove: (
  //   Altitude,
  //   Challenge,
  // ) => Challenge

  def gate: (
    InputFilter,
    Challenge,
  ) => Challenge

  object Gate {
    def unapply(c: Challenge): Option[(InputFilter, Challenge)] = ???
  }

  def photoAbove: Altitude => InputFilter

  def isEmpty: Challenge => Boolean
  def isReward: Challenge => Boolean
  def isPhoto: Input => Boolean

  def shorterOf[A]: (
    List[A],
    List[A],
  ) => List[A]

  type StepResult[A] =
    (
      (
        MultiSet[Reward],
        Map[Clue, ClueState],
      ),
      A,
    )

  def step: (
    Clue,
    Option[Input],
    Challenge,
  ) => StepResult[Challenge]

  def pumpChallenge(
    c: Challenge
  ): List[Input] => (
    (MultiSet[Reward], Map[Clue, ClueState]),
    Challenge,
  )

  def tellClue: Map[Clue, ClueState] => StepResult[Unit]

  def findClues: (Clue, Challenge) => Map[Clue, ClueState] = {
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

    def `inside point of interest`(
      c: Clue,
      poi: Point,
      d: Distance,
      r: Reward,
      p: Point,
      pic: Photo,
      is: List[Input],
    ): Boolean =
      within(poi, p, d) ==> {
        getRewards(pointOfInterest(c, poi, d, r), photo(p, pic) :: is) ==
          MultiSet.singleton(r)
      }

    def `outside point of interest`(
      c: Clue,
      poi: Point,
      d: Distance,
      r: Reward,
      p: Point,
      pic: Photo,
      is: List[Input],
    ): Boolean =
      !within(poi, p, d) ==> {
        getRewards(pointOfInterest(c, poi, d, r), photo(p, pic) :: is) ==
          getRewards(pointOfInterest(c, poi, d, r), is)
      }

    def `unmatching point of interest`(
      c: Clue,
      poi: Point,
      d: Distance,
      r: Reward,
      i: Input,
      is: List[Input],
    ): Boolean =
      !isPhoto(i) ==> {
        getRewards(pointOfInterest(c, poi, d, r), i :: is) ==
          getRewards(pointOfInterest(c, poi, d, r), is)
      }

    def `getRewards/clue`(
      k: Clue,
      c: Challenge,
    ) =
      (getRewards(clue(k, c), _)) <->
        (getRewards(c, _))

    def `getRewards/reward`(
      r: Reward,
      is: List[Input],
    ) = getRewards(reward(r), is) == MultiSet.singleton(r)

    def `matches/photoWithin`(
      p: Point,
      d: Distance,
      ph: Photo,
      poi: Point,
    ) =
      matches(photoWithin(p, d), photo(poi, ph)) ==
        within(p, poi, d)

    def `matches/photoWithin doesn't match for non-photos`(
      p: Point,
      d: Distance,
      ph: Photo,
      poi: Point,
      in: Input,
    ) =
      !isPhoto(in) ==>
        !matches(photoWithin(p, d), in)

    def `matches/photoAbove`(
      alt: Altitude,
      p: Point,
      ph: Photo,
      poi: Point,
    ) =
      matches(photoAbove(alt), photo(poi, ph)) ==
        above(alt, poi)

    def `matches/photoAbove doesn't match for non-photos`(
      alt: Altitude,
      in: Input,
    ) =
      !isPhoto(in) ==>
        !matches(photoAbove(alt), in)

    def `matches/always`(
      i: Input
    ) = matches(always, i) == true

    def `matches/never`(
      i: Input
    ) = matches(never, i) == false

    def `matches/andF`(
      f1: InputFilter,
      f2: InputFilter,
      i: Input,
    ) =
      matches(andF(f1, f2), i) ==
        (matches(f1, i) && matches(f2, i))

    def `matches/orF`(
      f1: InputFilter,
      f2: InputFilter,
      i: Input,
    ) =
      matches(orF(f1, f2), i) ==
        (matches(f1, i) || matches(f2, i))

    def `matches/notF`(
      f: InputFilter,
      i: Input,
    ) =
      matches(notF(f), i) ==
        !matches(f, i)

    // def `getRewards/photoWithin`(
    //   poi: Point,
    //   p: Point,
    //   pic: Photo,
    //   d: Distance,
    //   c: Challenge,
    //   is: List[Input],
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

    def `getRewards/gate`(
      f: InputFilter,
      i: Input,
      c: Challenge,
      is: List[Input],
    ) =
      matches(f, i) ==> {
        getRewards(gate(f, c), i :: is) ==
          getRewards(c, is)
      }

    def `getRewards/gate unmatched`(
      f: InputFilter,
      i: Input,
      c: Challenge,
      is: List[Input],
    ) =
      !matches(f, i) ==> {
        getRewards(gate(f, c), i :: is) ==
          getRewards(gate(f, c), is)
      }

    def `getRewards/gate empty`(
      f: InputFilter,
      c: Challenge,
    ) =
      getRewards(gate(f, c), Nil) ==
        MultiSet.empty

    def `pointOfInterest_`(
      c: Clue,
      p: Point,
      d: Distance,
      r: Reward,
    ) =
      pointOfInterest(c, p, d, r) ==
        clue(c, gate(photoWithin(p, d), reward(r)))

    def `bothCommutative`(
      c1: Challenge,
      c2: Challenge,
    ) = both(c1, c2) == both(c2, c1)

    def `bothAssociative`(
      c1: Challenge,
      c2: Challenge,
      c3: Challenge,
    ) = both(c1, both(c2, c3)) == both(both(c1, c2), c3)

    // def `bothIdempotent`(
    //   c: Challenge
    // ) = both(c, c) == c

    // exercise 5
    def `getRewards/both`(
      c1: Challenge,
      c2: Challenge,
      is: List[Input],
    ) =
      // contradiction: order of rewards depends on the order of params to both?
      getRewards(both(c1, c2), is) ==
        getRewards(c1, is) ++ getRewards(c2, is)

    def `getRewards/empty`(
      is: List[Input]
    ) = getRewards(empty, is) == MultiSet.empty

    def `getRewards/andThen`(
      c1: Challenge,
      c2: Challenge,
      is1: List[Input],
      is2: List[Input],
    ) =
      (completes(c1, is1) == Some(is2)) ==> {
        getRewards(andThen(c1, c2), is1) ==
          getRewards(c1, is1) ++ getRewards(c2, is2)
      }

    def `getRewards/andThen incomplete`(
      c1: Challenge,
      c2: Challenge,
      is: List[Input],
    ) =
      (completes(c1, is) == None) ==> {
        getRewards(andThen(c1, c2), is) ==
          getRewards(c1, is)
      }

    def `bothIdentity`(
      c: Challenge
    ) =
      (both(c, empty) == c) &&
        // commutativity implies this too
        (c == both(empty, c))

    def `andThen/gate`(
      f: InputFilter,
      c1: Challenge,
      c2: Challenge,
    ) =
      andThen(gate(f, c1), c2) ==
        gate(f, andThen(c1, c2))

    def `andThen/associative`(
      c1: Challenge,
      c2: Challenge,
      c3: Challenge,
    ) =
      andThen(c1, andThen(c2, c3)) ==
        andThen(andThen(c1, c2), c3)

    def `andThen/identity`(
      c: Challenge
    ) =
      andThen(empty, c) == c &&
        andThen(c, empty) == c

    def `falsified - complete/andThen`(
      f: InputFilter,
      c1: Challenge,
      c2: Challenge,
      is1: List[Input],
      is2: List[Input],
    ) = {
      def completes: (
        Challenge,
        List[Input],
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

    def `completes/empty`(
      is: List[Input]
    ) = completes(empty, is) == Some(is)

    def `completes/reward`(
      r: Reward,
      is: List[Input],
    ) = completes(reward(r), is) == Some(is)

    def `completes/clue`(
      k: Clue,
      c: Challenge,
      is: List[Input],
    ) = completes(clue(k, c), is) == completes(c, is)

    def `completes/both`(
      c1: Challenge,
      c2: Challenge,
      is: List[Input],
    ) =
      completes(both(c1, c2), is) == {
        for {
          i1 <- completes(c1, is)
          i2 <- completes(c2, is)
        } yield shorterOf(i1, i2)
      }

    def `step/both`(
      kctx: Clue,
      i: Option[Input],
      c1: Challenge,
      c2: Challenge,
    ) =
      step(kctx, i, both(c1, c2)) ==
        (step(kctx, i, c1), step(kctx, i, c2)).mapN(both)

    def `step/empty`(
      kctx: Clue,
      i: Option[Input],
    ) = step(kctx, i, empty) == Applicative[StepResult].pure(empty)

    def `step/reward`(
      kctx: Clue,
      i: Option[Input],
      r: Reward,
    ) = step(kctx, i, reward(r)) == Applicative[StepResult].pure(empty)

    def `step/gate`(
      kctx: Clue,
      i: Input,
      f: InputFilter,
      c: Challenge,
    ) =
      matches(f, i) ==> {
        step(kctx, Some(i), gate(f, c)) == step(kctx, None, c)
      }

    def `step/gate unmatched`(
      kctx: Clue,
      i: Input,
      f: InputFilter,
      c: Challenge,
    ) =
      !matches(f, i) ==> {
        step(kctx, Some(i), gate(f, c)) == Applicative[StepResult].pure(gate(f, c))
      }

    def `step/gate nothing`(
      kctx: Clue,
      f: InputFilter,
      c: Challenge,
    ) = step(kctx, None, gate(f, c)) == Applicative[StepResult].pure(gate(f, c))

    def `step/andThen complete`(
      kctx: Clue,
      i: Option[Input],
      c1: Challenge,
      c2: Challenge,
      r: (MultiSet[Reward], Map[Clue, ClueState]),
    ) = // if this input completes the c1 challenge with rewards `r`
      step(kctx, i, c1) == (r, empty) ==> {
        // then stepping once through the composition is the same as...
        step(kctx, i, andThen(c1, c2)) ==
          // getting that reward and whatever the reward is for no input of c2
          (r, step(kctx, None, c2)).flatten
      }

    def `step/andThen incomplete`(
      kctx: Clue,
      i: Option[Input],
      c1: Challenge,
      c2: Challenge,
      r: MultiSet[Reward],
    ) =
      // if this input completes with a non-empty challenge
      (!isEmpty(step(kctx, i, c1)._2)) ==> {
        // then stepping once through the composition is the same as...
        step(kctx, i, andThen(c1, c2)) ==
          // stepping once through c1, keeping the rest and passing that to an andThen
          step(kctx, i, c1).fmap(andThen(_, c2))
      }

    def `step/timeout matched`(
      kctx: Clue,
      c: Challenge,
      t: Time,
      cutoff: Time,
    ) =
      isAfter(cutoff, t) ==> {
        step(kctx, Some(time(t)), timeout(c, cutoff)) ==
          Applicative[StepResult].pure(empty)
      }

    def `step/timeout unmatched`(
      kctx: Clue,
      c: Challenge,
      t: Time,
      cutoff: Time,
    ) =
      !isAfter(t, cutoff) ==> {
        step(kctx, Some(time(t)), timeout(c, cutoff)) == step(kctx, None, c)
      }

    def `step/timeout nothing`(
      kctx: Clue,
      c: Challenge,
      t: Time,
      i: Option[Input],
    ) = step(kctx, None, timeout(c, t)) == step(kctx, None, c)

    def `pumpChallenge law`(
      c: Challenge
    ) = ??? // pumpChallenge(c) <->
    //   (_.map(_.some).prepended(None).foldM(c)(stepSwapped))

    def `getRewardsNew`(
      c: Challenge
    ) = (getRewards(c, _)) <-> pumpChallenge(c).andThen(_._1)

    def `completesNew`(
      c: Challenge
    ) = (completes(c, _)) <-> pumpChallenge(c).andThen(_._2).andThen(isEmpty)

    def `completes/gate`(
      f: InputFilter,
      i: Input,
      c: Challenge,
      is: List[Input],
    ) =
      matches(f, i) ==> {
        completes(gate(f, c), i :: is) ==
          Some(is)
      }

    def `completes/gate unmatched`(
      f: InputFilter,
      i: Input,
      c: Challenge,
      is: List[Input],
    ) =
      !matches(f, i) ==> {
        completes(gate(f, c), i :: is) ==
          completes(gate(f, c), is)
      }

    def `completes/gate empty`(
      f: InputFilter,
      c: Challenge,
    ) = completes(gate(f, c), Nil) == None

    def `completes/andThen`(
      c1: Challenge,
      c2: Challenge,
      is: List[Input],
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

    def `both/andThen/reward`(
      r: Reward,
      c1: Challenge,
      c2: Challenge,
    ) =
      both(andThen(reward(r), c1), c2) ==
        andThen(reward(r), both(c1, c2))

    def `eitherC:associative`(
      c1: Challenge,
      c2: Challenge,
      c3: Challenge,
    ) = eitherC(eitherC(c1, c2), c3) == eitherC(c1, eitherC(c2, c3))

    def `eitherC:commutative`(
      c1: Challenge,
      c2: Challenge,
    ) = eitherC(c1, c2) == eitherC(c2, c1)

    // todo: confirm?
    def `eitherC:identity`(
      c1: Challenge
    ) = eitherC(c1, bottom) == c1

    def `eitherC/andThen/reward`(
      r: Reward,
      c1: Challenge,
      c2: Challenge,
    ) =
      eitherC(andThen(reward(r), c1), c2) ==
        andThen(reward(r), eitherC(c1, c2))

    def `eitherC/empty`(
      c: Challenge
    ) =
      !isReward(c) ==> {
        eitherC(empty, c) == empty
      }

    def `bottomLaw`(
      c: Challenge
    ) = bottom == gate(never, c)

    def `sub:associative`(k1: Clue, k2: Clue, k3: Clue) =
      sub(k1, sub(k2, k3)) == sub(sub(k1, k2), k3)

    def `toList/hint`(s: String) = toList(hint(s)) == List(s)

    def `toList/sub`(k1: Clue, k2: Clue) = toList(sub(k1, k2)) == toList(k1) ++ toList(k2)

    def `sub/mempty`(k: Clue) = sub(k, noClue) == k && sub(noClue, k) == k

    def `clue/noClue`(c: Challenge) = clue(noClue, c) == c

    def `clue/sub`(k1: Clue, k2: Clue, c: Challenge) = clue(sub(k1, k2), c) == clue(k1, clue(k2, c))

    def `step/clue/empty`(
      kctx: Clue,
      i: Option[Input],
      k: Clue,
    ) =
      step(kctx, i, clue(k, empty)) ==
        tellClue(Map(sub(kctx, k) -> completed)) *>
        Applicative[StepResult].pure(empty)

    def `step/clue non-empty`(
      kctx: Clue,
      i: Option[Input],
      k: Clue,
      c: Challenge,
    ) =
      !isEmpty(c) ==> {
        step(kctx, i, clue(k, c)) ==
          tellClue(Map(sub(kctx, k) -> seen)) *>
          step(sub(kctx, k), i, c).map(clue(k, _))
      }

    def `step/eitherC empty`(
      kctx: Clue,
      i: Option[Input],
      c1: Challenge,
      c2: Challenge,
      `c2'`: Challenge,
    ) = {
      step(kctx, i, c1)._2 == empty &&
      step(kctx, i, c2)._2 == `c2'`
    } ==> {
      step(kctx, i, eitherC(c1, c2)) ==
        tellClue(findClues(kctx, `c2'`).fmap(seenToFailed)) *>
        step(kctx, i, c2) *>
        step(kctx, i, c1)
    }

    def `step/eitherC non-empty`(
      kctx: Clue,
      i: Option[Input],
      c1: Challenge,
      c2: Challenge,
      `c2'`: Challenge,
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

    def `reduce getRewards(clue (c (reward r))) to its simplest form via algebraic manipulation`(
      c: Clue,
      r: Reward,
    ) = {
      getRewards(clue(c, reward(r)), ???)
      //  via `getRewards/clue`
      getRewards(reward(r), ???)
      // via `getRewards/reward`
      MultiSet.singleton(r)
    }

    def `e2: what's the intuitive understanding + do stated semantics agree with you`(
      p1: Point,
      d1: Distance,
      p2: Point,
      d2: Distance,
      r: Reward,
      pho1: Photo,
      pho2: Photo,
    ) = {
      getRewards(
        gate(photoWithin(p1, d1), gate(photoWithin(p2, d2), reward(r))),
        photo(p1, pho1) :: photo(p2, pho2) :: Nil,
      )
      // getRewards/gate matched
      // matches(f, i) ==> {
      //   getRewards(gate(f, c), i :: is) ==
      //     getRewards(c, is)
      // }
      // yes matches: getRewards(gate(f, c), i :: is) == getRewards(c, is)
      getRewards(
        gate(photoWithin(p2, d2), reward(r)),
        photo(p2, pho2) :: Nil,
      )
      // again: getRewards(gate(f, c), i :: is) == getRewards(c, is)
      getRewards(reward(r), Nil)
      MultiSet.singleton(r)

    }

    def `e3: encode challenge to walk around the block twice, clockwise`(
      p1: Point,
      p2: Point,
      p3: Point,
      d1: Distance,
      d2: Distance,
      d3: Distance,
      r: Reward,
    ): Challenge = {
      val oneRound: Challenge => Challenge = (gate(locWithin(p1, d1), _))
        .compose(gate(locWithin(p2, d2), _))
        .compose(gate(locWithin(p3, d3), _))

      oneRound.compose(oneRound).apply(reward(r))
    }

    def `e4: prove`(
      c1: Challenge,
      c2: Challenge,
      c3: Challenge,
      c4: Challenge,
    ) = {
      val desired = both(c1, both(c2, both(c3, c4)))
      both(both(c1, c2), both(c3, c4))
      // by associativity
      both(c1, both(c2, both(c3, c4)))
    }

    def `e: reduce eitherC(reward r1) empty to its simplest form`(
      r: Reward
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
