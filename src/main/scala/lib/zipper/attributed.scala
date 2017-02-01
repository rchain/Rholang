// -*- mode: Scala;-*- 
// Filename:    attributed.scala 
// Authors:     lgm                                                    
// Creation:    Tue Jun 28 05:30:08 2011 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package coop.rchain.lib.zipper

trait AttributedTree[+Attr,+A]
extends Tree[Either[A,AttributedTree[Attr,A]]] {
  def attribute : Attr
}

case class AttrTreeItem[+Attr,+A](
  override val attribute : Attr,
  val attributedItem : A
) extends TreeItem[Either[A,AttributedTree[Attr,A]]](
  Left( attributedItem )
) with AttributedTree[Attr,A]

case class AttrTreeSection[+Attr,+A](
  override val attribute : Attr,
  val attributedSection : List[AttributedTree[Attr,A]]
) extends TreeSection[Either[A,AttributedTree[Attr,A]]](
  attributedSection
) with AttributedTree[Attr,A]
