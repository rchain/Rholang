package rholang.transforming.DelimcToLambda

// import rholang.transforming.DelimcToLambda.TypedLambda._
import rholang.transforming.DelimcToLambda.Prompt._
import rholang.transforming.DelimcToLambda.Seq._

import scalaz.Monad

/**
  * Created by weeeeeew on 2016-06-09.
  */

object DelimC {
  case class Cont[Ans, A, B](k: A => MC[Ans, B])

  def unCont[Ans, A, B](cont: Cont[Ans, A, B])(a: A) : MC[Ans, B] = cont match {
    case Cont(k) => k(a)
  }

  type MetaCont[Ans, A] = Seq[Cont, Ans, A]
  type SubCont[Ans, A, B] = SubSeq[Cont, Ans, A, B]

  type CCFun[Ans, A, B] = Cont[Ans, A, B] => MC[Ans, B]
  case class CC[Ans, A](c: CCFun[Ans, A, B] forSome {type B})

  def unCC[Ans, A](cc: CC[Ans, A])(cont: Cont[Ans, A, B] forSome {type B}) : MC[Ans, B] forSome {type B} = cc match {
    case CC(c) => c(cont)
  }

  case class MC[Ans, B](m: MetaCont[Ans, B] => P[Ans, Ans])

  def unMC[Ans, B](mc: MC[Ans, B])(metaCont: MetaCont[Ans, B]) : P[Ans, Ans] = mc match {
    case MC(m) => m(metaCont)
  }

  class CCMonad[Ans] extends Monad[Lambda[A => CC[Ans, A]]] {
    override final def point[A](e: => A): CC[Ans, A] = CC(k => unCont(k)(e))

    override final def bind[A, B](e1: CC[Ans, A])(e2: (A) => CC[Ans, B]): CC[Ans, B] =
      CC(k => unCC(e1)(Cont(v1 => unCC(e2(v1))(k))))
  }

  def appseg[Ans, A, B](k: Cont[Ans, A, B], a: A): MC[Ans, B] = unCont(k)(a)

  def runOne[Ans, A](c: CC[Ans, A]): MC[Ans, A] = {
    val initk = Cont(a => MC(mk => appmk(mk, a)))
    unCC(c)(initk)
  }

  def appmk[Ans, A](mk: MetaCont[Ans, A], a: A): P[Ans, Ans] = mk match {
    case EmptyS()        => ???
    case PushP(_, mk2)   => appmk(mk2, a)
    case PushSeg(k, mk2) => unMC(appseg(k, a))(mk2)
  }

  def runTwo[Ans](c: MC[Ans, Ans]): P[Ans, Ans] = unMC(c)(EmptyS())

  def runCC[A](ce: CC[Ans, A] forSome {type Ans}): A = runP(runTwo(runOne(ce)))
}