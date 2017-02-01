package rholang.transforming.DelimcToLambda

import scalaz.Monad

/**
  * Created by weeeeeew on 2016-06-07.
  */
object Prompt {
  case class Prompt[Ans,A](p : Int)

  def unPrompt[Ans,A](prompt: Prompt[Ans,A]): Int = prompt match {
    case Prompt(p) => p
  }

  case class P[Ans,A](f : Int => (Int, A))

  def unP[Ans,A](p: P[Ans,A])(i: Int): (Int,A) = p match {
    case P(f) => f(i)
  }

  class PMonad[Ans] extends Monad[({type Lambda[A] = P[Ans, A]})#Lambda] {
    override final def point[A](e: => A): P[Ans,A] = P[Ans,A]((s: Int) => (s,e))

    override final def bind[A, B](pa: P[Ans,A])(e2: A => P[Ans,B]): P[Ans,B] =
      P((s1: Int) => unP(pa)(s1) match {
        case (s2,v1) => unP(e2(v1))(s2)
      })
  }

  def runP[Ans](pe: P[Ans,Ans]): Ans = unP(pe)(0)._2

  def newPromptName[Ans,A]: P[Ans, Prompt[Ans,A]] = P((np: Int) => (np+1, Prompt(np)))

  sealed trait Equal[A,B]
  case class IsEqual[A]() extends Equal[A,A]
  case class IsNotEqual[A,B]() extends Equal[A,B]

  def eqPrompt[Ans,A,B](p1: Prompt[Ans,A], p2: Prompt[Ans,B]): Equal[A,B] =
    if (unPrompt(p1) == unPrompt(p2)) {
      IsEqual().asInstanceOf[Equal[A,B]]
    } else {
      IsNotEqual()
    }

}
