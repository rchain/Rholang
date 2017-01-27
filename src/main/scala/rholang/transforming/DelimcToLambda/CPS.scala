package rholang.transforming.DelimcToLambda

import scalaz.Monad

/**
  * Created by weeeeeew on 15/07/16.
  */
object CPS {
  case class K[Ans,A](f: A => Ans)

  def unK[Ans,A](k: K[Ans,A])(a: A): Ans = k match {
    case K(f) => f(a)
  }

  case class M[Ans,A](f: K[Ans,A] => Ans)

  def unM[Ans,A](m: M[Ans,A])(k: K[Ans,A]): Ans = m match {
    case M(f) => f(k)
  }

  class MonadM[Ans] extends Monad[Lambda[A => M[Ans,A]]] {
    override final def point[A](e: => A): M[Ans, A] = M(k => unK(k)(e))

    override final def bind[A,B](ma: M[Ans,A])(e2: A => M[Ans,B]): M[Ans,B] =
      M(k => unM(ma)(K(v1 => unM(e2(v1))(k))))
  }

  def callcc[Ans,A](f: K[Ans,A] => M[Ans,A]): M[Ans,A] = M(k => unM(f(k))(k))

  def abort[Ans,A](a: Ans): M[Ans,A] = M(_ => a)

  def throw_[Ans,A,B](k: K[Ans,A], m: M[Ans,A]): M[Ans,B] = M(_ => unM(m)(k))

  def c[Ans,A](f: K[Ans,A] => Ans): M[Ans,A] = callcc(k => abort(f(k)))

  def runM[Ans](m: M[Ans,Ans]): Ans = unM(m)(K(identity))
}
