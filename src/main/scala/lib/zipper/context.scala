// -*- mode: Scala;-*- 
// Filename:    context.scala 
// Authors:     lgm                                                    
// Creation:    Tue May 25 03:04:00 2010 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package coop.rchain.lib.zipper

trait Context[+A] {
}
case class Top[+A]( ) extends Context[A] {
  override def toString() : String = {
    "[]"
  }
}
class TreeContext[+A](
  val left : List[Tree[A]],
  val ctxt : Context[A],
  val right : List[Tree[A]]
) extends Context[A] {
  override def toString() : String = {
    "[" + left + ", " + ctxt + ", " + right + "]"
  }
}
object TreeContext {
  def apply[A](
    left : List[Tree[A]],
    ctxt : Context[A],
    right : List[Tree[A]] ) = {
    new TreeContext( left, ctxt, right )
  }
  def unapply[A]( ctxt : TreeContext[A] )
  : Option[( List[Tree[A]], Context[A], List[Tree[A]] )] = {
    Some( ( ctxt.left, ctxt.ctxt, ctxt.right ) )
  }
}

class LabeledTreeContext[L, +A] (
  val label : L,
  override val left : List[Tree[A]],
  override val ctxt : Context[A],
  override val right : List[Tree[A]]
) extends TreeContext[A]( left, ctxt, right ) {
  override def toString() : String = {
    label + "[" + left + ", " + ctxt + ", " + right + "]"
  }
}
object LabeledTreeContext {
  def apply[L, A](
    label : L,
    left : List[Tree[A]],
    ctxt : Context[A],
    right : List[Tree[A]]
  ) = {
    new LabeledTreeContext( label, left, ctxt, right )
  }
  def unapply[L, A]( ctxt : LabeledTreeContext[L,A] )
  : Option[( L, List[Tree[A]], Context[A], List[Tree[A]] )] = {
    Some( ( ctxt.label, ctxt.left, ctxt.ctxt, ctxt.right ) )
  }
}
