// -*- mode: Scala;-*- 
// Filename:    ast.scala 
// Authors:     lgm                                                    
// Creation:    Tue May 25 04:19:13 2010 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package coop.rchain.lib.zipper

case class Token[A](
  override val item : A
) extends TreeItem[A]( item )
case class AST[A](
  override val section : List[Tree[A]]
) extends TreeSection[A]( section )


