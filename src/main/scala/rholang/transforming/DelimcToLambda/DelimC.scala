package rholang.transforming.DelimcToLambda

// import rholang.transforming.DelimcToLambda.TypedLambda._
// import rholang.transforming.DelimcToLambda.Prompt._
// import rholang.transforming.DelimcToLambda.Seq._

// import scalaz.Monad

/**
  * Created by weeeeeew on 2016-06-09.
  */
/*
object DelimC {
  case class Cont[Ans, A, B](k: Expr[A => MC[Ans, B]])

  def unCont[Ans, A, B](cont: Cont[Ans, A, B], a: A) : MC[Ans, B] = cont match {
    case Cont(k) => k(a)
  }

  type MetaCont[Ans, A] = Seq[Cont[_,_,_], Ans, A]
  type SubCont[Ans, A, B] = SubSeq[Cont[_,_,_], Ans, A, B]
  case class CC[Ans, A, B](c: Cont[Ans, A, B] => MC[Ans, B])

  def unCC[Ans, A, B](cc: CC[Ans, A, B], cont: Cont[Ans, A, B]) : MC[Ans, B] = cc match {
    case CC(c) => c(cont)
  }

    trait CC[Ans, A] {
    type X
    val c: Cont[Ans, A, X] => MC[Ans, X]
  }


  type ContToMC[Ans, A, B] = Cont[Ans, A, B] => MC[Ans, B]

  case class CC[Ans, A](c: ContToMC[Ans, A, B forSome { type B }])


  //def unCC[Ans, A](cc: CC[Ans, A]) = cc.c

  case class MC[Ans, B](m: MetaCont[Ans, B] => P[Ans, Ans])

  def unMC[Ans, B](mc: MC[Ans, B], metaCont: MetaCont[Ans, B]) : P[Ans, Ans] = mc match {
    case MC(m) => m(metaCont)
  }

  class CCMonad[Ans,C] extends Monad[({type l[A] = CC[Ans, A]})#l] {
    override def point[A](e: => A): CC[Ans, A] =
      new CC[Ans, A]{
        type X=C
        val c = (cont: Cont[Ans, A, C]) => unCont(c, e)
      }

    override def bind[A, B](cc: CC[Ans, A])(e2: A => CC[Ans, B]): CC[Ans, B] = {
      val e1 = cc.c
      new CC[Ans, B] {
        type X = cc.X

        val c: Cont[Ans, B, cc.X] => MC[Ans, cc.X] = k => e1[Ans,A,cc.X]( Cont[Ans,A,cc.X]((v1: A) => e2(v1).c(???)))
      }
    }
  }
}

*/
