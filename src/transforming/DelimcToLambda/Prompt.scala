package transforming.DelimcToLambda

import scalaz.Monad
import transforming.DelimcToLambda.TypedLambda._

/**
  * Created by weeeeeew on 2016-06-07.
  */
object Prompt {
  case class Prompt[Ans, A](p : Expr[Integer])
  case class P[Ans, A](f : Expr[Integer => (Integer, A)])

  def unP[Ans, A](p: P[Ans, A], i: Expr[Integer]) : Expr[(Integer,A)] = p match {
    case P(f) => EApp[Integer, (Integer, A)](f, i, ???)
  }

  trait Equal[A,B]
  case class IsEqual[A](a1: A, a2: A) extends Equal[A,A]
  case class IsNotEqual[A,B](a: A, b: B) extends Equal[A,B]

  class PMonad[Ans] extends Monad[({type l[A] = P[Ans, A]})#l] {
    override def bind[A, B](p: P[Ans, A])(e2: A => P[Ans, B]) : P[Ans, B] =
      P[Ans,B]((s1: Integer) => unP[Ans,A](p, s1) match {
        case (s2,v1) => unP[Ans,B](e2(v1), s2)
      })

    override def point[A](e: => A): P[Ans, _][A] = P[Ans,_][A]((s: Integer) => (s,e))
  }

  def runP[Ans](pe: P[Ans, Ans]) : Ans = unP(pe,0)._2

  def newPromptName[Ans, A] : P[Ans, Prompt[Ans, A]] = P(np => (np+1, Prompt(np)))

  def eqPrompt[Ans, A, B](pa : Prompt[Ans, A], pb : Prompt[Ans, B]) : Equal[A,B] = (pa, pb) match {
    case (Prompt(p1), Prompt(p2)) => if p1 == p2 {
      IsEqual
    } else {
      IsNotEqual
    }
  }
}
