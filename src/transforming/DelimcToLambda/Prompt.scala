package transforming.DelimcToLambda

import scalaz.Monad
import parsing.lambda.Absyn._

/**
  * Created by weeeeeew on 2016-06-07.
  */
object Prompt {
  case class Prompt[Ans, A](p : Int)
  case class P[Ans, A](f : Int => (Int, A))

  def unP[Ans, A](p : P[Ans, A], a : Int) : (Int, A) = p match {
    case P(f) => f(a)
  }

  trait Equal[A,B]
  case class IsEqual[A](a1 : A, a2 : A) extends Equal[A,A]
  case class IsNotEqual[A,B](a : A, b : B) extends Equal[A,B]

  class PromptMonad[Ans] extends Monad[P[Ans, _]] {
    def point[A](e : A) : P[Ans, A] = P(s => (s,e))

    def bind[A, B](m : P[Ans, _][A])(e2 : A => P[Ans, _]) = P(s1 => unP(m,s1) match {
      case (s2,v1) => unP(e2(v1),s2)
    })
  }

  def runP[Ans](pe : P[Ans, Ans]) : Ans = unP(pe,0)._2

  def newPromptName[Ans, A] : P[Ans, Prompt[Ans, A]] = P(np => (np+1, Prompt(np)))

  def eqPrompt[Ans, A, B](pa : Prompt[Ans, A], pb : Prompt[Ans, B]) : Equal[A,B] = (pa, pb) match {
    case (Prompt(p1), Prompt(p2)) => if p1 == p2 {
      IsEqual
    } else {
      IsNotEqual
    }
  }
}
