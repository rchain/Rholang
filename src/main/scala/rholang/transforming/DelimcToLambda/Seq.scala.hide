package rholang.transforming.DelimcToLambda

import Prompt._

/**
  * Created by weeeeeew on 2016-06-07.
  */
object Seq {
  sealed trait Seq[-Contseg[_,_,_],-Ans,-A]

  case class EmptyS[Contseg[_,_,_],Ans]()
    extends Seq[Contseg,Ans,Ans]

  case class PushP[Contseg[_,_,_],Ans,A](p: Prompt[Ans,A], seq: Seq[Contseg,Ans,A])
    extends Seq[Contseg,Ans,A]

  case class PushSeg[Contseg[_,_,_],Ans,A,B](contseg: Contseg[Ans,A,B], seq: Seq[Contseg,Ans,B])
    extends Seq[Contseg,Ans,A]

  type SubSeq[+Contseg[_,_,_],-Ans,-A,-B] = Seq[Contseg,Ans,B] => Seq[Contseg,Ans,A]

  def emptySubSeq[Contseg[_,_,_],Ans,A]: SubSeq[Contseg,Ans,A,A] = identity

  def appendSubSeq[Contseg[_,_,_],Ans,A,B,C](b2a: SubSeq[Contseg,Ans,A,B], c2b: SubSeq[Contseg,Ans,B,C]): SubSeq[Contseg,Ans,A,C] =
    b2a compose c2b

  def pushSeq[Contseg[_,_,_],Ans,A,B](subSeq: SubSeq[Contseg,Ans,A,B], seq: Seq[Contseg,Ans,B]) : Seq[Contseg,Ans,A] =
    subSeq(seq)

  def splitSeq[Contseg[_,_,_],Ans,A,B](p: Prompt[Ans,B], seq: Seq[Contseg,Ans,A]): (SubSeq[Contseg,Ans,A,B], Seq[Contseg,Ans,B]) =
    seq match {
      case EmptyS()         => sys.error("Prompt was not found on the stack")
      case PushP(p2, sk)    => eqPrompt(p2,p) match {
        case IsEqual()    => (emptySubSeq, sk.asInstanceOf[Seq[Contseg,Ans,B]])
        case IsNotEqual() => ??? //TODO figure this out.
      }
      case PushSeg(seg, sk) => splitSeq(p, sk) match {
        case (subk,sk2) => (appendSubSeq(PushSeg(seg,_), subk), sk2)
      }
    }
}
