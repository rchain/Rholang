// -*- mode: Scala;-*- 
// Filename:    search.scala 
// Authors:     lgm                                                    
// Creation:    Thu Apr 14 04:34:22 2011 
// Copyright:   Not supplied 
// Description: 
// ------------------------------------------------------------------------

package coop.rchain.lib.zipper

import scala.collection.mutable.Map

trait ZipperMap[A]
extends Map[Location[A],Tree[A]] {
}
