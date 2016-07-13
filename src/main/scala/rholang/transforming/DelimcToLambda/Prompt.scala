package rholang.transforming.DelimcToLambda

import scalaz.Monad
import rholang.transforming.DelimcToLambda.TypedLambda._

/**
  * Created by weeeeeew on 2016-06-07.
  */
object Prompt {
  case class Prompt[Ans,A](p : ETyped[Integer])

  case class P[Ans,A](f : ETyped[Integer => (Integer, A)])

  def unP[Ans,A](p: P[Ans,A], i: ETyped[Integer]) : ETyped[(Integer,A)] = p match {
    case P(f) => applyAbs(f,i)
  }

  class MonadP[Ans] extends Monad[P[Ans,_]] {
    override def point[A](e: => A): P[Ans, A] = P[Ans, A]((s: Integer) => (s,e))

    override def bind[A, B](pa: P[Ans,A])(e2: A => P[Ans,B]): P[Ans,B] =
      P[Ans,B]((s1: Integer) => unP[Ans,A](pa, s1) match {
        case (s2,v1) => unP[Ans,B](e2(v1), s2)
      })
  }

  def runP[Ans](pe: P[Ans,Ans]) = snd2(unP(pe, typedInt(0)))
/*
  def newPromptName[Ans, A] : P[Ans, Prompt[Ans, A]] = for{np <- newName}
    yield newAbs(np, TSimple[Integer], tuple2(add(newVar(np), typedInt(1)), Prompt(newVar(np)))

  def eqPrompt[Ans, A, B](pa : Prompt[Ans, A], pb : Prompt[Ans, B]) : Equal[A,B] = (pa, pb) match {
    case (Prompt(p1), Prompt(p2)) if p1 == p2 => IsEqual(pa,pb)
    case (Prompt(p1), Prompt(p2))             => IsNotEqual(pa,pb)
  }
*/
}
