package transforming.DelimcToLambda

import transforming.DelimcToLambda.Prompt._

/**
  * Created by weeeeeew on 2016-06-07.
  */
object Seq {
  sealed trait Seq[Contseg[_,_,_], Ans, A]
  case class EmptyS[Contseg[_,_,_], Ans]() extends Seq[Contseg, Ans, Ans]
  case class PushP[Contseg[_,_,_], Ans, A](p: Prompt[Ans, A], seq: Seq[Contseg, Ans, A]) extends Seq[Contseg, Ans, A]
  case class PushSeg[Contseg[_,_,_], Ans, A, B](seg: Contseg[Ans, A, B], seq: Seq[Contseg, Ans, B]) extends Seq[Contseg, Ans, A]

  type SubSeq[Contseg[_,_,_], Ans, A, B] = Seq[Contseg, Ans, B] => Seq[Contseg, Ans, A]

  def emptySubSeq[Contseg[_,_,_], Ans, A] : SubSeq[Contseg, Ans, A, A] = identity[Seq[Contseg, Ans, A]]

  def appendSubSeq[Contseg[_,_,_], Ans, A, B, C](subSeq1: SubSeq[Contseg, Ans, A, B], subSeq2: SubSeq[Contseg, Ans, B, C])
   : SubSeq[Contseg, Ans, A, C] = subSeq1 compose subSeq2

  def pushSeq[Contseg[_,_,_], Ans, A, B](subSeq: SubSeq[Contseg, Ans, A, B], seq: Seq[Contseg, Ans, B]) : Seq[Contseg, Ans, A] =
    subSeq(seq)
}
